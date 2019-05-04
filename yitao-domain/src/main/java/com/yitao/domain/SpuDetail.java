package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: SpuDetail
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 10:27
 */

@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "spu_id")
    private Long spuId;

    @Column(name = "description")
    private String description;

    @Column(name = "generic_spec")
    private String genericSpec;

    @Column(name = "special_spec")
    private String specialSpec;

    @Column(name = "packing_list")
    private String packingList;

    @Column(name = "after_service")
    private String afterService;

}
