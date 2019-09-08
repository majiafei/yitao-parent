package com.yitao.order.service;

import com.yitao.common.entity.UserInfo;
import com.yitao.order.entity.OrderDTO;

/**
 * <p>
 *     订单接口
 * </p>
 * @ProjectName: yitao
 * @Package: com.yitao.order.service
 * @ClassName: OrderService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 19:30
 */
public interface OrderService {
    Long createOrder(OrderDTO orderDTO, UserInfo userInfo);
}
