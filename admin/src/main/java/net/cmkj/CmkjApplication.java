package net.cmkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 * 
 * @author Yuan
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CmkjApplication
{
    public static void main(String[] args) throws UnknownHostException {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext application = SpringApplication.run(CmkjApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        System.out.println("\n\033[32m--------------------------------------------------------------------\n" +
                " Application is running! Access URLs:\n" +
                " Local:        \033[34mhttp://localhost:" + port + "/\033[32m\n" +
                " External:     \033[34mhttp://" + ip + ":" + port + "/\033[32m\n" +
                " Swagger:      \033[34mhttp://" + ip + ":" + port + "/swagger-ui/index.html\033[32m\n" +
                "--------------------------------------------------------------------\033[0m");


    }
}
