package com.yitao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.dto
 * @ClassName: SkuDTO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/2 10:07
 */

@Data
public class SkuDTO implements Serializable {

    private Boolean enable;

    private String images;

    private String indexes;

    private String ownSpec;

    private Double price;

    private Integer stock;

    private String title;

}
