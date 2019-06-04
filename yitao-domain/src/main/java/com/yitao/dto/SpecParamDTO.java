package com.yitao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: SpecParamDTO
 * @Auther: admin
 * @Date: 2019/6/4 09:40
 * @Description:
 */

@Data
public class SpecParamDTO implements Serializable {

    private Long id;

    private Boolean generic;

    private Long groupId;

    private Long gid;

    private String name;

    private Boolean numeric;

    private Boolean searching;

    private String segments;

    private Long cid;

    private String unit;

}
