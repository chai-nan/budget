package net.cmkj.framework.config.properties;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * license配置属性
 */
@Data
public class LicenseProperties implements Serializable {
    private String licenseId;       // 许可证ID
    private String username;        // 授权用户
    private Date validFrom;         // 生效时间
    private Date validTo;           // 过期时间
    private String macAddress;      // 绑定的MAC地址（可选，防止复制到其他机器）
    private Date lastCheckTime;     // 上次验证时间

    // 构造器、getter、setter
    public LicenseProperties(String licenseId, String username, Date validFrom, Date validTo, String macAddress) {
        this.licenseId = licenseId;
        this.username = username;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.macAddress = macAddress;
        this.lastCheckTime = new Date(); // 初始化时默认设为当前时间
    }
}
