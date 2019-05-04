package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 *     订单实体类
 * </p>
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: Order
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/3 20:08
 */

@Data
@Table(name = "tb_brand")
public class Order{

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_pay")
    private Long totalPay;

    /** 实付金额 */
    @Column(name = "actual_pay")
    private Long actualPay;

    @Column(name = "promotion_ids")
    private String promotionIds;

    @Column(name = "payment_type")
    private Byte paymentType;

    @Column(name = "post_fee")
    private Long postFee;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "shipping_name")
    private String shippingName;

    @Column(name = "shipping_code")
    private String shippingCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "buyer_message")
    private String buyerMessage;

    @Column(name = "buyer_nick")
    private String buyerNick;

    @Column(name = "buyer_rate")
    private Byte buyerRate;

    @Column(name = "receiver_state")
    private String receiverState;

    @Column(name = "receiver_city")
    private String receiverCity;

    @Column(name = "receiver_district")
    private String receiverDistrict;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_mobile")
    private String receiver_mobile;

    @Column(name = "receiver_zip")
    private String receiver_zip;

    @Column(name = "receiver")
    private String receiver;

    /** 发票类型 */
    @Column(name = "invoice_type")
    private Integer invoiceType;

    /** 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端 */
    @Column(name = "source_type")
    private Integer sourceType;

}
