package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: Sku
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 9:21
 */

@Data
@Table(name = "tb_sku")
public class Sku {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long skuId;

    @Column(name = "spu_id")
    private Long spuId;

    @Column(name = "title")
    private String title;

}
