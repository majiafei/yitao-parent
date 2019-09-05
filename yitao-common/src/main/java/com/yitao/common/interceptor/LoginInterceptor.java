package com.yitao.common.interceptor;

import com.yitao.common.interceptor.properties.FilterProperties;
import com.yitao.common.interceptor.properties.JwtProperties;
import com.yitao.common.utils.CookieUtils;
import com.yitao.common.utils.JwtUtils;
import com.yitao.common.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *     全局拦截器
 * </p>
 * @ClassName: LoginInterceptor
 * @Auther: majiafei
 * @Date: 2019/9/5 11:37
 * @Description:
 */
@Slf4j
@Component
@EnableConfigurationProperties({FilterProperties.class, JwtProperties.class})
public class LoginInterceptor implements WebMvcConfigurer {

    @Autowired(required = false)
    private FilterProperties filterProperties;

    @Autowired(required = false)
    private JwtProperties jwtProperties;


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                try {
                    // 获取相对uri
                    String requestURI = request.getRequestURI();
                    // 判断uri是否在放行的uri中,如果在，就放行,不在就进行鉴权
                    if (isAllowPath(requestURI)) {
                        return true;
                    } else {
                        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
                        // 鉴权通过
                        JwtUtils.getUserInfo(RsaUtils.getPublicKey(jwtProperties.getPubKeyPath()), token);

                        return true;
                    }
                } catch (Exception e) {
                    log.error("未登录,非法访问");
                }
                return false;
            }
        });
    }

    private boolean isAllowPath(String requestUri) {
        for (String uri : filterProperties.getAllowPaths()) {
            if (requestUri.startsWith(uri)) {
                return true;
            }
        }

        return false;
    }

}

