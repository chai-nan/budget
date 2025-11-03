package net.cmkj.web.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.framework.web.domain.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 * 
 * @author Yuan
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @SaCheckPermission("monitor:server:list")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
