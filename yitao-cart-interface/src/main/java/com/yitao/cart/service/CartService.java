package com.yitao.cart.service;

import com.yitao.cart.entity.Cart;
import com.yitao.common.entity.UserInfo;

import java.util.List;

/**
 * @ClassName: CartService
 * @Auther: admin
 * @Date: 2019/9/6 14:56
 * @Description:
 */
public interface CartService {

    /**
     * 将单个商品添加到购物车
     * @param cart 购物车的信息
     * @param userInfo 用户的信息
     */
    void addCart(Cart cart, UserInfo userInfo);

    /**
     * 从redis中 获取购物车的信息
     * @param userInfo
     * @return
     */
    List<Cart> getCarts(UserInfo userInfo);

}
