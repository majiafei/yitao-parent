package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: Spu
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 9:49
 */

@Data
@Table(name = "tb_spu")
public class Spu implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long spuId;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "cid1")
    private Long cid1;

    @Column(name = "cid2")
    private Long cid2;

    @Column(name = "cid3")
    private Long cid3;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "saleable")
    private Byte saleable;

    @Column(name = "valid")
    private Byte valid;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

}
