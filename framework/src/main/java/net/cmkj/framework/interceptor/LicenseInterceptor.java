package net.cmkj.framework.interceptor;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.framework.web.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * license拦截器
 */
@Component
public class LicenseInterceptor implements HandlerInterceptor {

    @Autowired
    private LicenseService licenseService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证License
        if (!licenseService.isLicenseValid()) {
            // 若依前端需要JSON格式的错误响应
            response.setContentType("application/json;charset=UTF-8");
            AjaxResult result = AjaxResult.error("License验证失败：请联系管理员获取有效授权");
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }
        return true; // 验证通过，继续处理请求
    }
}
