package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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

    @Column(name = "images")
    private String images;

    @Column(name = "price")
    private Long price;

    /** 特有规格属性在spu属性模板中的对应下标组合 */
    @Column(name = "indexes")
    private String indexes;

    @Column(name = "own_spec")
    private String ownSpec;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

}
