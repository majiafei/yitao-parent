package com.yitao.vo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.vo
 * @ClassName: BrandVO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 19:23
 */

@Data
public class BrandVO implements Serializable {

    private Long id;
    private String name;
    private String image;
    private String letter;

}
