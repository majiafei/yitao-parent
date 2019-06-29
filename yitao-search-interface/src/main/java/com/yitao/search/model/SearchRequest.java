package com.yitao.search.model;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;

import java.util.Map;

/**
 * <p>
 *     查询的搜索参数
 * </p>
 * @Package: com.yitao.search.model
 * @ClassName: SearchRequest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/29 22:43
 */
@Data
public class SearchRequest {
    // 每页数量
    private Integer DEFAULT_SIZE = 100;
    // 默认的页码
    private Integer DEFAULT_PAGE = 1;
    // 关键词
    private String keywords;
    // 页码
    private Integer page;
    // 过滤字段
    private Map<String, String> filter;

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }

        return Math.max(DEFAULT_PAGE, page);
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

}
