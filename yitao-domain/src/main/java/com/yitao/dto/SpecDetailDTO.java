package com.yitao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.dto
 * @ClassName: SpecDetailDTO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/2 10:10
 */

@Data
public class SpecDetailDTO implements Serializable {

    /** 售后服务 */
    private String afterService;

    /** 描述 */
    private String description;

    /** 通用的规格参数 */
    private String genericSpec;

    /**  包装清单 */
    private String packingList;

    /** 特殊的规格参数 */
    private String specialSpec;
}
