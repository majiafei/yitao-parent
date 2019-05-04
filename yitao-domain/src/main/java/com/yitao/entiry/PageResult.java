package com.yitao.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.entiry
 * @ClassName: PageResult
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 11:44
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    // 总记录数
    private long total;
    // 每页显示的数据集合
    private List<T> rows;

    public static <T> PageResult<T> build(long total, List<T> rows) {
        return new PageResult<>(total, rows);
    }
}
