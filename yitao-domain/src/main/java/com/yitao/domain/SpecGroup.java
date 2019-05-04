package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: SpecGroup
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 10:10
 */

@Data
@Table(name = "tb_spec_group")
public class SpecGroup {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long spectGroupId;

    @Column(name = "cid")
    private Long categoryId;

    @Column(name = "name")
    private String spectGroupName;

}
