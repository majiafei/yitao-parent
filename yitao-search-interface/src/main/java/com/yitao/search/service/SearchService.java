package com.yitao.search.service;

import com.yitao.domain.Spu;
import com.yitao.search.model.Goods;
import com.yitao.search.model.SearchRequest;
import com.yitao.search.model.SearchResult;
import org.springframework.data.domain.PageRequest;

/**
 * @ClassName: SearchService
 * @Auther: admin
 * @Date: 2019/6/5 10:45
 * @Description:
 */
public interface SearchService {

    /**
     * 向索引库中导入商品
     * @param spu
     */
    Goods buildGoods(Spu spu);

    SearchResult<Goods> search(SearchRequest searchRequest);

    void insertOrUpdateGoods(Long spuId);
}
