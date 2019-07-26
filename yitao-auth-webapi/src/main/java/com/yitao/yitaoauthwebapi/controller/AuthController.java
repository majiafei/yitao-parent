package com.yitao.yitaoauthwebapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.auth.properties.JwtProperties;
import com.yitao.auth.service.AuthService;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.CookieUtils;
import com.yitao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: AuthController
 * @Auther: admin
 * @Date: 2019/7/26 15:00
 * @Description:
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Reference(check = false)
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户授权
     * @param user
     * @param response
     * @param request
     * @return
     */
    @PostMapping("accredit")
    @ResponseBody
    public ResponseEntity<Void> login(User user, HttpServletResponse response, HttpServletRequest request) {
        String token = authService.login(user);
        if (StringUtils.hasText(token)) {
            throw new ServiceException("用户名或者密码错误");
        }

        CookieUtils.cookieBuider().request(request)
                .response(response)
                .charset("utf-8")
                .httpOnly(true)
                .maxAge(jwtProperties.getCookieMaxAge())
                .addCookie(jwtProperties.getCookieName(), token);
        return ResponseEntity.ok().build();
    }

}
