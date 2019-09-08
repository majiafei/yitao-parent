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

        addCart(cart, hashOperations);
    }

    @Override
    public List<Cart> getCarts(UserInfo userInfo) {
        String key = CART_PREFIX + userInfo.getId();
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        List<Object> carts = boundHashOperations.values();

        return carts.stream().map(s -> JsonUtils.toObject(String.valueOf(s), Cart.class)).collect(Collectors.toList());
    }

    @Override
    public void batchAddCart(List<Cart> cartList, UserInfo userInfo) {
        // 构造key，前缀+userId
        String key = CART_PREFIX + userInfo.getId();
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);
        // 遍历cartList
        for (Cart cart : cartList) {
           addCart(cart, hashOperations);
        }
    }

    @Override
    public void increment(Long id, Integer num, UserInfo userInfo) {
        String key = CART_PREFIX + userInfo.getId();
        // 获取该用户的购物车信息
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);
        String skuId = String.valueOf(id);

        if (hashOperations.hasKey(skuId)) {
            Cart cart = JsonUtils.toObject(String.valueOf(hashOperations.get(skuId)), Cart.class);
            // 将数量相加
            cart.setNum(num);

            // 将cart重新设置到redis中
            hashOperations.put(skuId, JsonUtils.fromObjectToString(cart));
        }
    }

    @Override
    public void deleteCart(Long skuId, UserInfo userInfo) {
        String key = CART_PREFIX + userInfo.getId();
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);

        String skuIdStr = String.valueOf(skuId);
        if (hashOperations.hasKey(skuIdStr)) {
            // 删除该sku
            hashOperations.delete(skuIdStr);
        }
    }

    private void addCart(Cart cart, BoundHashOperations hashOperations) {
        String skuId = String.valueOf(cart.getSkuId());
        if (hashOperations.hasKey(skuId)) {
            Cart cartInRedis = JsonUtils.toObject(String.valueOf(hashOperations.get(skuId)), Cart.class);
            // 将数量相加
            cartInRedis.setNum(cartInRedis.getNum() + cart.getNum());

            // 数量相加完后，再次存到redis中
            hashOperations.put(skuId, JsonUtils.fromObjectToString(cartInRedis));
        } else {
            // 没有，直接添加到购物车
            hashOperations.put(skuId, JsonUtils.fromObjectToString(cart));
        }
    }
}
