package net.cmkj.framework.web.service;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import net.cmkj.common.core.domain.entity.SysRole;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.system.service.ISysMenuService;
import net.cmkj.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限处理
 * 
 * @author Yuan
 */
@Component
public class SysPermissionService implements StpInterface
{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取角色数据权限
     * 
     * @return 角色权限信息
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SysUser user = ((LoginUser) StpUtil.getSession().get(StpUtil.getTokenValue())).getUser();
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return new ArrayList<>(roles);
    }

    /**
     * 获取菜单数据权限
     * 
     * @return 菜单权限信息
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SysUser user = ((LoginUser)StpUtil.getSession().get(StpUtil.getTokenValue())).getUser();
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            List<SysRole> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles))
            {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles)
                {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            }
            else
            {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return new ArrayList<>(perms);
    }
}
