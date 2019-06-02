package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: SpecParam
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 10:22
 */

@Data
@Table(name = "tb_spec_param")
public class SpecParam {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long specParamId;

    @Column(name = "cid")
    private Long categoryId;

    @Column(name = "group_id")
    private Long specGroupId;

    @Column(name = "name")
    private String specParamName;

    /**  是否是数字类型 */
    @Column(name = "'numeric'")
    private Byte numeric;

    @Column(name = "unit")
    private String unit;

    @Column(name = "generic")
    private Byte generic;

    @Column(name = "searching")
    private Byte searching;

    /**
     * 数值类型参数，如果需要搜索，则添加分段间隔值，
     * 如CPU频率间隔：0.5-1.0
     */
    @Column(name = "segments")
    private String segments;

}
