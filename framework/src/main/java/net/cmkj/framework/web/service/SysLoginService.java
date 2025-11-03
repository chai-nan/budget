package net.cmkj.framework.web.service;

import cn.dev33.satoken.stp.StpUtil;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.common.constant.CacheConstants;
import net.cmkj.common.constant.Constants;
import net.cmkj.common.constant.UserConstants;
import net.cmkj.common.core.domain.entity.SysRole;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.common.core.redis.RedisCache;
import net.cmkj.common.enums.UserStatus;
import net.cmkj.common.exception.ServiceException;
import net.cmkj.common.exception.user.*;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.MessageUtils;
import net.cmkj.common.utils.ServletUtils;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.common.utils.ip.IpUtils;
import net.cmkj.framework.manager.AsyncManager;
import net.cmkj.framework.manager.factory.AsyncFactory;
import net.cmkj.system.service.ISysConfigService;
import net.cmkj.system.service.ISysMenuService;
import net.cmkj.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 登录校验方法
 * 
 * @author Yuan
 */
@Component
@Slf4j
public class SysLoginService
{

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysMenuService menuService;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        // 验证码校验
        validateCaptcha(username, code, uuid);
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        SysUser user = null;
        try {
            user = userService.selectUserByUserName(username);
            if (StringUtils.isNull(user))
            {
                log.info("登录用户：{} 不存在.", username);
                throw new ServiceException(MessageUtils.message("user.not.exists"));
            }
            else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
            {
                log.info("登录用户：{} 已被删除.", username);
                throw new ServiceException(MessageUtils.message("user.password.delete"));
            }
            else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
            {
                log.info("登录用户：{} 已被停用.", username);
                throw new ServiceException(MessageUtils.message("user.blocked"));
            }
            passwordService.validate(user,username,password);
            setRolePermission(user);
            LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user);
            setUserAgent(loginUser);
            StpUtil.getSession().set(StpUtil.getTokenValue(),loginUser);
        }catch (Exception e)
        {
            if (e instanceof UserPasswordNotMatchException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user.getUserId());
        // 生成token
        return StpUtil.getTokenValue();
    }

    public void setRolePermission(SysUser user)
    {
        List<SysRole> roles = user.getRoles();
        if (!roles.isEmpty())
        {
            // 设置permissions属性，以便数据权限匹配权限
            for (SysRole role : roles)
            {
                Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                role.setPermissions(rolePerms);
            }
        }
    }

    public void setUserAgent(LoginUser loginUser)
    {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 客户端浏览器
        loginUser.setBrowser(userAgent.getBrowser().getName());
        // 获取客户端操作系统
        loginUser.setOs(userAgent.getOperatingSystem().getName());
        StpUtil.login(loginUser.getUserId(), userAgent.getOperatingSystem().getDeviceType() == DeviceType.COMPUTER ? "PC" : "APP");
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            if (captcha == null)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            redisCache.deleteObject(verifyKey);
            if (!code.equalsIgnoreCase(captcha))
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
