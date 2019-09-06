package com.yitao.yitaocartwebapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.cart.entity.Cart;
import com.yitao.cart.service.CartService;
import com.yitao.common.entity.UserInfo;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.interceptor.properties.JwtProperties;
import com.yitao.common.utils.CookieUtils;
import com.yitao.common.utils.JwtUtils;
import com.yitao.common.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * <p>
 *     购物车控制器
 * </p>
 * @ClassName: CartController
 * @Auther: admin
 * @Date: 2019/9/6 14:31
 * @Description:
 */
@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired(required = false)
    private JwtProperties jwtProperties;

    @Reference(check = false)
    private CartService cartService;

    /**
     * 单个商品添加到购物车
     * @param cart 购物车
     * @return
     */
    @PostMapping
    public ResponseEntity addCart(@RequestBody Cart cart, HttpServletRequest request) {
        try {
            String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
            UserInfo userInfo = JwtUtils.getUserInfo(RsaUtils.getPublicKey(jwtProperties.getPubKeyPath()), token);
            // 添加到购物车
            cartService.addCart(cart, userInfo);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("add cart", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/list")
    public List<Cart> getCarts(HttpServletRequest request) {
        try {
            return cartService.getCarts(getUserInfoFromToken(request));
        } catch (Exception e) {
            log.error("get carts", e);
            throw new ServiceException("用户为授权");
        }
    }

    private UserInfo getUserInfoFromToken(HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        UserInfo userInfo = JwtUtils.getUserInfo(RsaUtils.getPublicKey(jwtProperties.getPubKeyPath()), token);
        return userInfo;
    }

}
