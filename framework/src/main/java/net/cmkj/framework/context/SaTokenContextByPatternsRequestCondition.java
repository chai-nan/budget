package net.cmkj.framework.context;

import cn.dev33.satoken.spring.SaTokenContextForSpringInJakartaServlet;
import cn.dev33.satoken.spring.pathmatch.SaPatternsRequestConditionHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Yuan
 * @date 2024-7-11
 * @describe：
 */

@Primary
@Component
public class SaTokenContextByPatternsRequestCondition extends SaTokenContextForSpringInJakartaServlet {

    @Override
    public boolean matchPath(String pattern, String path) {
        return SaPatternsRequestConditionHolder.match(pattern, path);
    }

}