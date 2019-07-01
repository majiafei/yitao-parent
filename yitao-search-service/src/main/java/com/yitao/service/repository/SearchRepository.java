package com.yitao.service.repository;

import com.yitao.search.model.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @ClassName: SearchRepository
 * @Auther: admin
 * @Date: 2019/6/10 13:46
 * @Description:
 */
public interface SearchRepository extends ElasticsearchRepository<Goods, Long> {
    Goods findAllById(Long id);
}
