package com.yitao.mapper;

import com.yitao.domain.Order;
import com.yitao.domain.OrderDeail;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @ProjectName: house
 * @Package: com.yitao.mapper
 * @ClassName: OrderDetailMapper
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 11:05
 */
public interface OrderDetailMapper extends Mapper<OrderDeail>, InsertListMapper<OrderDeail> {
}
