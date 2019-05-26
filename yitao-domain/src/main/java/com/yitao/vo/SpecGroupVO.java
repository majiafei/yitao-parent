package com.yitao.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Package: com.yitao.vo
 * @ClassName: SpecGroupVO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 10:05
 */

@Getter
@Setter
public class SpecGroupVO implements Serializable {

    private Long id;
    private String name;

}
