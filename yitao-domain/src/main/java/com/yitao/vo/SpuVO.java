package com.yitao.vo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: com.yitao.vo
 * @ClassName: SpuVO
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 15:14
 */
@Data
public class SpuVO implements Serializable {

    private Long id;

    private String title;

    private String subTitle;

    private String cname;

    private String bname;

    private Byte saleable;

    private Byte valid;

    private String createTime;

    private String lastUpdateTime;

}
