package com.yitao.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * @ClassName: CookieUtils
 * @Auther: majiafei
 * @Date: 2019/7/26 14:19
 * @Description:
 */
@Slf4j
public class CookieUtils {

    /**
     * 根据名称获取cookie的值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, null);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName, String chartset) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        try {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    if (StringUtils.hasText(chartset)) {
                        return URLDecoder.decode(cookie.getValue(), chartset);
                    } else {
                        return cookie.getValue();
                    }
                }
            }

            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static CookieBuider cookieBuider() {
        return new CookieBuider();
    }

    public static class CookieBuider {
        private HttpServletRequest request;
        private HttpServletResponse response;
        private String charset;
        private Integer maxAge;
        private Boolean httpOnly = false;

        public CookieBuider request(HttpServletRequest request) {
            this.request = request;
            return this;
        }

        public CookieBuider response(HttpServletResponse response) {
            this.response = response;
            return this;
        }

        public CookieBuider charset(String charset) {
            this.charset = charset;
            return this;
        }

        public CookieBuider maxAge(Integer maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public CookieBuider httpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }

        public void addCookie(String cookieName, String cookieValue) {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (maxAge != null && maxAge > 0) {
                cookie.setMaxAge(maxAge);
            }
            cookie.setHttpOnly(httpOnly);
            response.addCookie(cookie);
        }
    }

}
