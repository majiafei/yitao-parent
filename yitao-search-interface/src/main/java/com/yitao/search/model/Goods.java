package com.yitao.search.model;

import com.sun.corba.se.spi.ior.ObjectKey;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName: Goods
 * @Auther: majiafei
 * @Date: 2019/6/5 10:53
 * @Description:
 */

@Document(indexName = "goods", type = "docs", shards = 1, replicas = 1)
@Data
public class Goods {

    /** spuid */
    @Id
    private Long id;

    /** 描述，标题，类目，品牌的组合 */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    @Field(type = FieldType.Keyword, index = false)
    private String title;

    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    @Field(type = FieldType.Long)
    private Long brandId;

    @Field(type = FieldType.Long)
    private Long cid1;

    @Field(type = FieldType.Long)
    private Long cid2;

    @Field(type = FieldType.Long)
    private Long cid3;

    @Field(type = FieldType.Keyword, index = false)
    private String cid1Name;

    @Field(type = FieldType.Keyword, index = false)
    private String cid2Name;

    @Field(type = FieldType.Keyword, index = false)
    private String cid3Name;

    @Field(type = FieldType.Keyword)
    private String decription;

    /** 规格参数 */
    private Map<String, Object> specParams;

    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    private Date createTime;

}
