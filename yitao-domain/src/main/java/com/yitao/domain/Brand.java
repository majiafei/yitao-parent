package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 *     品牌实体类
 * </p>
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: Brand
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/3 19:59
 */

@Data
@Table(name = "tb_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long branId;

    @Column(name = "name")
    private String brandName;

    @Column(name = "image")
    private String brandImage;

    @Column(name = "letter")
    private String brandLetter;

}
