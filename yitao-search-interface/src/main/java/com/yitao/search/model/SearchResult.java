package com.yitao.search.model;

import com.yitao.domain.Category;
import com.yitao.vo.BrandVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: house
 * @Package: com.yitao.search.model
 * @ClassName: SearchResult
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/29 23:01
 */
@Data
@NoArgsConstructor
public class SearchResult<Goods> extends PageResult<Goods> implements Serializable {
    private static final long serialVersionUID = 8652079038727336732L;

    private List<BrandVO> brandVOList;
    private List<Category> categoryList;
    // 规格参数
    private Map<String, Object> specs;

    public SearchResult(long total, Integer totalPages, List<Goods> goodsList,
                        List<BrandVO> brandVOList, List<Category> categoryList, Map<String, Object> specs) {
        super(total, totalPages, goodsList);
        this.brandVOList = brandVOList;
        this.categoryList = categoryList;
        this.specs = specs;
    }


}
