package com.yitao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.dto
 * @ClassName: BrandDTO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 11:48
 */
@Data
public class BrandDTO implements Serializable {

    /** 搜索条件 */
    private String key;

    /**  当前页码 */
    private Integer page;

    /** 每页显示的行数 */
    private Integer rows;

    /** 排序条件 */
    private String sortBy;

    /**  是否倒序 */
    private Boolean desc;

}
