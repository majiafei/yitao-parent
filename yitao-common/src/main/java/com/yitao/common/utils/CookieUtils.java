package com.yitao.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
            try {
                if (org.apache.commons.lang3.StringUtils.isBlank(charset)) {
                    charset = "utf-8";
                }

                if (cookieValue == null) {
                    cookieValue = "";
                } else if (org.apache.commons.lang3.StringUtils.isNotBlank(charset)) {
                    cookieValue = URLEncoder.encode(cookieValue, charset);
                }
                Cookie cookie = new Cookie(cookieName, cookieValue);
//                if (maxAge != null && maxAge > 0)
                    cookie.setMaxAge(maxAge);
                if (null != request)// 设置域名的cookie
                    cookie.setDomain(getDomainName(request));
                cookie.setPath("/");

                cookie.setHttpOnly(httpOnly);
                response.addCookie(cookie);
            } catch (Exception e) {
                log.error("Cookie Encode Error.", e);
            }
        }

        /**
         * 得到cookie的域名
         */
        private String getDomainName(HttpServletRequest request) {
            String domainName = null;

            // 必须在nginx的配置中添加 proxy_set_header Host $host;
            // 否则获取不到真实的域名信息
            String serverName = request.getRequestURL().toString();
            if (serverName == null || serverName.equals("")) {
                domainName = "";
            } else {
                serverName = serverName.toLowerCase();
                serverName = serverName.substring(7);
                final int end = serverName.indexOf("/");
                serverName = serverName.substring(0, end);
                final String[] domains = serverName.split("\\.");
                int len = domains.length;
                if (len > 3) {
                    // www.xxx.com.cn
                    domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
                } else if (len <= 3 && len > 1) {
                    // xxx.com or xxx.cn
                    domainName = domains[len - 2] + "." + domains[len - 1];
                } else {
                    domainName = serverName;
                }
            }

            if (domainName != null && domainName.indexOf(":") > 0) {
                String[] ary = domainName.split("\\:");
                domainName = ary[0];
            }
            return domainName;
        }
    }

}
