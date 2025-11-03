package net.cmkj.framework.manager;

import com.alibaba.fastjson2.JSONObject;
import net.cmkj.common.utils.CryptoUtils;
import net.cmkj.common.utils.HardwareUtils;
import net.cmkj.framework.config.properties.LicenseProperties;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;

public class LicenseGenerator {

    // 生成RSA密钥对（公钥给程序，私钥自己保存用于生成License）
    public static void generateRsaKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); // 2048位密钥
        KeyPair keyPair = generator.generateKeyPair();

        // 保存公钥（程序中用于验签）
        String publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        try (FileWriter fw = new FileWriter("license/publicKey.txt")) {
            fw.write(publicKeyStr);
        }

        // 保存私钥（自己保管，用于生成License）
        String privateKeyStr = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        try (FileWriter fw = new FileWriter("license/privateKey.txt")) {
            fw.write(privateKeyStr);
        }
    }

    // 生成加密的License文件
    public static void generateLicenseFile(
            LicenseProperties license,
            String privateKeyStr,
            String aesKey,
            String licenseFilePath
    ) throws Exception {
        // 1. License对象转JSON
        String licenseJson = JSONObject.toJSONString(license);

        // 2. 用私钥签名（防止篡改）
        PrivateKey privateKey = CryptoUtils.loadPrivateKey(privateKeyStr);
        String sign = CryptoUtils.rsaSign(licenseJson, privateKey);

        // 3. 拼接JSON和签名（格式：json|sign）
        String content = licenseJson + "|" + sign;

        // 4. AES加密内容
        String encryptedContent = CryptoUtils.aesEncrypt(content, aesKey);

        // 5. 写入License文件
        try (FileWriter fw = new FileWriter(licenseFilePath)) {
            fw.write(encryptedContent);
        }
    }

    public static void main(String[] args) throws Exception {
        // 生成RSA密钥对（只需要执行一次）
        generateRsaKeyPair();

        // 构造License信息（例如：授权用户"testUser"，有效期1年，绑定当前MAC）
        Date validFrom = new Date();
        Date validTo = new Date(validFrom.getTime() + 365L * 24 * 60 * 60 * 1000); // 1年后过期
        LicenseProperties license = new LicenseProperties(
                "LIC-251023",
                "anyang",
                validFrom,
                validTo,
                HardwareUtils.getMacAddress() // 绑定当前机器的MAC
                //"08:94:EF:5B:64:CE"
        );

        // 从文件读取私钥（实际中私钥需安全保管）
        String privateKeyStr = FileUtils.readFileToString(new File("license/privateKey.txt"), StandardCharsets.UTF_8).trim(); // 需实现简单的文件读取工具
        String aesKey = FileUtils.readFileToString(new File("license/aesKey.txt"), StandardCharsets.UTF_8).trim(); // AES密钥（16位，需与程序中一致）

        // 生成License文件
        generateLicenseFile(license, privateKeyStr, aesKey, "license/license.lic");
    }

}
