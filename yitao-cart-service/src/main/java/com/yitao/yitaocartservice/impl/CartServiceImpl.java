package com.yitao.yitaocartservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.cart.entity.Cart;
import com.yitao.cart.service.CartService;
import com.yitao.common.entity.UserInfo;
import com.yitao.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: CartServiceImpl
 * @Auther: admin
 * @Date: 2019/9/6 14:57
 * @Description:
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CART_PREFIX = "yt:cart:uid:";

    @Override
    public void addCart(Cart cart, UserInfo userInfo) {
        String key = CART_PREFIX + userInfo.getId();
        // 查看redis中是否已经该商品
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);
        String skuId = String.valueOf(cart.getSkuId());
        // 如果有，直接将数量相加
        if (hashOperations.hasKey(skuId)) {
            Cart oldCart = JsonUtils.toObject(hashOperations.get(skuId).toString(), Cart.class);
            oldCart.setNum(oldCart.getNum() + cart.getNum());
            hashOperations.put(skuId, JsonUtils.fromObjectToString(oldCart));
        } else {

            // 没有，将该cart的信息添加到redis中
            hashOperations.put(String.valueOf(skuId), JsonUtils.fromObjectToString(cart));
        }
    }

    @Override
    public List<Cart> getCarts(UserInfo userInfo) {
        String key = CART_PREFIX + userInfo.getId();
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        List<Object> carts = boundHashOperations.values();

        return carts.stream().map(s -> JsonUtils.toObject(String.valueOf(s), Cart.class)).collect(Collectors.toList());
    }
}
