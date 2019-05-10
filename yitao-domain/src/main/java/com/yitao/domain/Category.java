package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: Category
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/3 20:06
 */

@Data
@Table(name = "tb_category")
public class Category implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "is_parent")
    private Boolean isParent;

    @Column(name = "sort")
    private Integer sort;

}
