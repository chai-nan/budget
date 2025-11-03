package net.cmkj.framework.web.service;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.PostConstruct;
import net.cmkj.common.utils.CryptoUtils;
import net.cmkj.common.utils.HardwareUtils;
import net.cmkj.framework.config.properties.LicenseProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class LicenseService {
    // AES密钥（从resources文件读取）
    private String aesKey;

    // 缓存验证结果和License信息
    private final AtomicBoolean licenseValid = new AtomicBoolean(false);
    private LicenseProperties cachedLicense;

    /**
     * 初始化：读取aesKey并执行首次验证
     */
    @PostConstruct
    public void init() throws Exception {
        // 读取aesKey（从resources/license/aesKey.txt）
        this.aesKey = FileUtils.readFileToString(new File("license/aesKey.txt"), StandardCharsets.UTF_8).trim(); // 去除可能的空格/换行
        if (this.aesKey.length() != 16 && this.aesKey.length() != 24 && this.aesKey.length() != 32) {
            throw new Exception("AES密钥长度错误（需16/24/32位）");
        }
        // 执行首次验证
        validate();
    }

    /**
     * 验证License合法性
     * @throws Exception
     */
    public void validate() throws Exception {
        // 1.读取公钥和License文件
        String publicKeyStr = FileUtils.readFileToString(new File("license/publicKey.txt"), StandardCharsets.UTF_8).trim();
        String encryptedLicense = FileUtils.readFileToString(new File("license/license.lic"), StandardCharsets.UTF_8).trim();

        // 2. AES解密
        String decryptedContent = CryptoUtils.aesDecrypt(encryptedLicense, aesKey);
        String[] parts = decryptedContent.split("\\|");
        if (parts.length != 2) {
            throw new Exception("License格式错误");
        }
        String licenseJson = parts[0];
        String sign = parts[1];

        // 3. RSA验签
        PublicKey publicKey = CryptoUtils.loadPublicKey(publicKeyStr);
        if (!CryptoUtils.rsaVerify(licenseJson, sign, publicKey)) {
            throw new Exception("License已被篡改");
        }

        // 4. 解析License并验证有效期和硬件
        LicenseProperties license = JSONObject.parseObject(licenseJson, LicenseProperties.class);
        Date now = new Date();
        if (now.before(license.getValidFrom()) || now.after(license.getValidTo())) {
            throw new Exception("License已过期或未生效（有效期：" + license.getValidFrom() + "至" + license.getValidTo() + "）");
        }

        // 5. 验证硬件绑定（若依部署可能在服务器，绑定服务器MAC）
        String currentMac = HardwareUtils.getMacAddress();
        if (!currentMac.equalsIgnoreCase(license.getMacAddress())) {
            throw new Exception("License与当前服务器不匹配（MAC：" + currentMac + "）");
        }

        // 验证通过后，更新上次验证时间为当前时间
        license.setLastCheckTime(new Date());

        // 验证通过，更新缓存
        cachedLicense = license;
        licenseValid.set(true);
    }

    /**
     * 检查License是否有效（供拦截器快速调用）
     */
    public boolean isLicenseValid() {
        // 定期重新验证（如每小时），防止License过期后仍能访问
        if (System.currentTimeMillis() - cachedLicense.getLastCheckTime().getTime() > 3600_000) {
            try {
                validate();
            } catch (Exception e) {
                licenseValid.set(false);
            }
        }
        return licenseValid.get();
    }
}
