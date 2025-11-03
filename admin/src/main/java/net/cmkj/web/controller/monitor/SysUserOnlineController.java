package net.cmkj.web.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import cn.dev33.satoken.stp.StpUtil;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.system.domain.SysUserOnline;
import net.cmkj.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 * 
 * @author Yuan
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController
{
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @SaCheckPermission("monitor:online:list")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName)
    {
        List<String> sessionIdList = StpUtil.searchSessionId("", 0, -1, false);
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String sessionId : sessionIdList) {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            List<TokenSign> tokenSignList = session.getTokenSignList();
            for (TokenSign token: tokenSignList ) {
                LoginUser user = (LoginUser) session.get(token.getValue());
                user.setToken(token.getValue());
                if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
                else if (StringUtils.isNotEmpty(ipaddr))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
                else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
                else
                {
                    userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
                }
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @SaCheckPermission("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId)
    {
        StpUtil.kickoutByTokenValue(tokenId);
        return success();
    }
}
