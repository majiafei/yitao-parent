package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 *     订单详情实体类
 * </p>
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: OrderDeail
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/3 21:03
 */

@Data
@Table(name = "tb_order_detail")
public class OrderDeail {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long orderDeailId;

    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "sku_id")
    private Long skuId;

    /** 购买数量 */
    @Column(name = "num")
    private Integer num;

    /** 商品标题 */
    @Column(name = "title")
    private String itemTitle;

    @Column(name = "own_spec")
    private String ownSpec;

    /** 商品价格 */
    @Column(name = "price")
    private Long itemPrice;

    /** 商品图片 */
    @Column(name = "image")
    private String itemImage;

}
