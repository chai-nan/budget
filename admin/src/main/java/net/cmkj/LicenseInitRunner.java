package net.cmkj;

import net.cmkj.framework.web.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LicenseInitRunner implements CommandLineRunner {

    @Autowired
    private LicenseService licenseService;

    @Override
    public void run(String... args) throws Exception {
        try {
            licenseService.init();
            System.out.println("License初始化验证通过");
        } catch (Exception e) {
            System.err.println("License初始化失败：" + e.getMessage());
            // 验证失败，强制退出程序
            System.exit(1);
        }
    }
}
