package com.yitao.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     订单信息
 * </p>
 * @ProjectName: yitao
 * @Package: com.yitao.order
 * @ClassName: OrderDTO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 19:18
 */
@Data
public class OrderDTO implements Serializable {
    private Long addressId;
    private List<OrderDetail> orderDetailList;
}
