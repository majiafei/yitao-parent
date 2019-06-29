package com.yitao.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package: com.yitao.vo
 * @ClassName: SkuVO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/2 12:18
 */

@Data
public class SkuVO implements Serializable {

    private Boolean enable;

    private String images;

    private String indexes;

    private String ownSpec;

    private Double price;

    private Integer stock;

    private String title;
}
