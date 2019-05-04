package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 *     库存实体类
 * </p>
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: Stock
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 10:42
 */

@Data
@Table(name = "tb_stock")
public class Stock {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "sku_id")
    private Long skuId;

    /** 可秒杀库存 */
    @Column(name = "seckill_stock")
    private Integer seckillStock;

    @Column(name = "seckill_total")
    private Integer seckillTotal;

    @Column(name = "stock")
    private Integer stock;

}
