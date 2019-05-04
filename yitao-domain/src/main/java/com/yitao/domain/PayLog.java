package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 *     支付日志
 * </p>
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: PayLog
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/3 21:16
 */

@Data
@Table(name = "tb_pay_log")
public class PayLog {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_fee")
    private Long totalFee;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "transaction_id")
    private String transactionId;

    /** 1 未支付, 2已支付, 3 已退款, 4 支付错误, 5 已关闭 */
    @Column(name = "status")
    private Integer payStatus;

    @Column(name = "pay_type")
    private Byte payType;

    @Column(name = "bank_type")
    private String bankType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "closed_time")
    private Date closedTime;

    @Column(name = "refund_time")
    private Date refundTime;

}
