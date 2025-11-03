package net.cmkj.web.controller.system;

import cn.dev33.satoken.stp.StpUtil;
import net.cmkj.common.constant.Constants;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysMenu;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.domain.model.LoginBody;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.framework.manager.AsyncManager;
import net.cmkj.framework.manager.factory.AsyncFactory;
import net.cmkj.framework.web.service.SysLoginService;
import net.cmkj.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 * 
 * @author Yuan
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = new HashSet<>(StpUtil.getRoleList());
        // 权限集合
        Set<String> permissions = new HashSet<>(StpUtil.getPermissionList());

        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 退出接口
     * @return
     */
    @RequestMapping(value = "/logout",method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResult logout()
    {
        try {
            String userName = SecurityUtils.getUsername();
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }catch (Exception e){
            //前端校验 如果没有这个token 将不进行记录
        }
        StpUtil.logout();
        return AjaxResult.success("退出成功");
    }
}
