package com.yitao.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Package: com.yitao.search.model
 * @ClassName: PageResult
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/29 22:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 7202277663045304261L;

    // 总记录数
    private long total;
    // 总页数
    private Integer totalPages;
    // 当前页的数据
    private List<T> rows;

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
