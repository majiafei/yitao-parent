package com.yitao.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.dto
 * @ClassName: SpecGroupDTO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 10:34
 */
@Getter
@Setter
public class SpecGroupDTO implements Serializable {

    private Long id;
    private Long cid;
    private String name;

}
