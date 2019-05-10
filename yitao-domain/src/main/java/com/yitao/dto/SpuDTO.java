package com.yitao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.dto
 * @ClassName: SpuDTO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 14:36
 */

@Data
public class SpuDTO implements Serializable {

    /** 当前页码 */
    private Integer page;

    /**  每页显示的行数 */
    private Integer rows;

    /**  搜索条件 */
    private String key;

    /** 是否上下架 */
    private Boolean saleable;

}
