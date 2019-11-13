package com.yitao.yitaoauthwebapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.auth.properties.JwtProperties;
import com.yitao.auth.service.AuthService;
import com.yitao.common.entity.UserInfo;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.CookieUtils;
import com.yitao.common.utils.JwtUtils;
import com.yitao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
        if (StringUtils.isEmpty(token)) {
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

    @GetMapping("verify")
    @ResponseBody
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("YT_TOKEN") String token, HttpServletRequest request, HttpServletResponse response) {
        try {

            // 验证改用户是否已经登录
            UserInfo userInfo = JwtUtils.getUserInfo(jwtProperties.getPublicKey(), token);
            // 如果登录，则刷新token
            String newToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.cookieBuider().maxAge(jwtProperties.getCookieMaxAge())
                    .httpOnly(true)
                    .charset("utf-8")
                    .response(response)
                    .request(request)
                    .addCookie(jwtProperties.getCookieName(), newToken);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * 注销登录
     *
     * @param token
     * @param response
     * @return
     */
    @GetMapping("logout")
    public ResponseEntity<Void> logout(@CookieValue("YT_TOKEN") String token, HttpServletResponse response, HttpServletRequest request) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(token)) {
            CookieUtils.cookieBuider().response(response).maxAge(0).request(request).addCookie(jwtProperties .getCookieName(), token);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
