package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 *    订单状态实体类
 * </p>
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: OrderStatus
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/3 21:09
 */

@Data
@Table(name = "tb_order_status")
public class OrderStatus {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "order_id")
    private Long orderId;

    /**
     *   1、未付款 2、已付款,未发货 3、已发货,未确认
     *   4、交易成功 5、交易关闭 6、已评价
     */
    @Column(name = "status")
    private Integer orderStatus;

    @Column(name = "create_time")
    private Date createTiem;

    @Column(name = "payment_time")
    private Date paymentTime;

    /** 发货时间 */
    @Column(name = "consign_time")
    private Date consignTime;

    /** 交易完成时间 */
    @Column(name = "end_time")
    private Date endTime;

    /** 交易关闭时间 */
    @Column(name = "close_time")
    private Date closeTime;

    @Column(name = "comment_time")
    private Date commentTime;

}
