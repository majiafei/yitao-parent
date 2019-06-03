package com.yitao.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    private Long id;

    /** 是否上下架 */
    private Boolean saleable;

    private Long brandId;

    private Long cid1;

    private Long cid2;

    private Long cid3;

    private List<SkuDTO> skus;

    private SpecDetailDTO spuDetail;

    private String subTitle;

    private String title;
}
