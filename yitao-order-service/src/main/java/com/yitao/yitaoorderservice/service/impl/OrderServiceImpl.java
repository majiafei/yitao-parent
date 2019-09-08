package com.yitao.yitaoorderservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yitao.common.entity.UserInfo;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.IdWorker;
import com.yitao.common.utils.ObjectUtils;
import com.yitao.domain.Order;
import com.yitao.domain.OrderDeail;
import com.yitao.domain.OrderStatus;
import com.yitao.domain.Sku;
import com.yitao.mapper.OrderDetailMapper;
import com.yitao.mapper.OrderMapper;
import com.yitao.mapper.OrderStatusMapper;
import com.yitao.order.OrderStatusEnum;
import com.yitao.order.entity.OrderDTO;
import com.yitao.order.entity.OrderDetail;
import com.yitao.order.service.OrderService;
import com.yitao.yitaoorderservice.client.SkuClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *     订单业务实现类
 * </p>
 * @ProjectName: yitao
 * @Package: com.yitao.yitaoorderservice.service.impl
 * @ClassName: OrderServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 19:32
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public Long createOrder(OrderDTO orderDTO, UserInfo userInfo) {
        // 生成订单号
        long orderId = idWorker.nextId();
        // 创建订单信息
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setActualPay(0L);
        order.setOrderId(orderId);
        order.setUserId(String.valueOf(userInfo.getId()));

        // 创建订单详情信息
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new ServiceException("请选择要购买的商品");
        }
        // 将订单详情转换成map
        Map<Long, Integer> skuNumMap = orderDetailList.stream().collect(Collectors.toMap(OrderDetail::getSkuId, OrderDetail::getNum));
        // 获取所有sku的信息
        List<Sku> skuList = skuClient.getSkuList(skuNumMap.keySet());
        if (CollectionUtils.isEmpty(skuList)) {
            throw new ServiceException("查询的商品信息不存在");
        }

        List<OrderDeail> orderDeailList = Lists.newArrayList();
        Long totalPay = 0L;
        for (Sku sku : skuList) {
            OrderDeail orderDeail = new OrderDeail();
            orderDeail.setItemImage(StringUtils.substringBefore(sku.getImages(), ","));
            orderDeail.setSkuId(sku.getSkuId());
            orderDeail.setItemPrice(ObjectUtils.toLong(sku.getPrice())); // TODO
            orderDeail.setItemTitle(sku.getTitle());
            orderDeail.setNum(skuNumMap.get(sku.getSkuId()));
            orderDeail.setOrderId(orderId);
            orderDeail.setOwnSpec(sku.getOwnSpec());

            orderDeailList.add(orderDeail);

            // 计算总价
            totalPay += ObjectUtils.toLong(sku.getPrice()) * skuNumMap.get(sku.getSkuId());
        }

        order.setActualPay(totalPay); // TODO 实际支付=总费用+邮费-优惠
        order.setTotalPay(totalPay);

        // 订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTiem(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.INIT.getCode());

        // 插入数据到数据库
        orderMapper.insert(order);
        orderDetailMapper.insertList(orderDeailList);
        orderStatusMapper.insert(orderStatus);

        // 减库存


        // 异步删除购物车中的数据

        return orderId;
    }
}
