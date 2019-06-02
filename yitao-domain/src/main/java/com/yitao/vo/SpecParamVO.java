package com.yitao.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.vo
 * @ClassName: SpecParamVO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/1 17:52
 */
@Data
public class SpecParamVO implements Serializable {

    private Long id;
    private String name;
    private Byte generic;
    private Byte numeric;
    private String unit;

}
