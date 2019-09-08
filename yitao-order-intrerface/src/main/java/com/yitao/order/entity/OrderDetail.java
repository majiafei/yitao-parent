package com.yitao.order.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     订单详情
 * </p>
 * @ProjectName: yitao
 * @Package: com.yitao.order.entity
 * @ClassName: OrderDetail
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 19:20
 */
@Data
public class OrderDetail implements Serializable {
    private Long skuId;
    private Integer num;
}
