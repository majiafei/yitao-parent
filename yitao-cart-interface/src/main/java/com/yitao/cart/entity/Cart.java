package com.yitao.cart.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Cart
 * @Auther: admin
 * @Date: 2019/9/6 14:34
 * @Description:
 */
@Data
public class Cart implements Serializable {

    // sku的id
    private Long skuId;

    // sku的标题
    private String title;

    // sku的某一张图片
    private String image;

    // sku的价格
    private Double price;

    // 数量
    private Integer num;

    // sku的参数
    private String ownSpec;



}
