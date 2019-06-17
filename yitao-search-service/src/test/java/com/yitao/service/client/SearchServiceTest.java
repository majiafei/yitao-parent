package com.yitao.service.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.Spu;
import com.yitao.search.model.Goods;
import com.yitao.search.service.SearchService;
import com.yitao.service.YitaoSearchServiceApplicationTests;
import com.yitao.service.repository.SearchRepository;
import com.yitao.vo.SpuVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

/**
 * @ClassName: SearchServiceTest
 * @Auther: admin
 * @Date: 2019/6/5 16:38
 * @Description:
 */
public class SearchServiceTest extends YitaoSearchServiceApplicationTests {

    @Reference
    private SearchService searchService;

    private static final String serverUrl = "http://api.yitao.com";

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void importGoods() {
        ResponseEntity<Spu> responseEntity = RestTemplateUtils.getForEntity(serverUrl + "/api/item/spu/{spuId}", Spu.class, 2L);
        Spu spu = responseEntity.getBody();
        Goods goods = searchService.importGoods(spu);
        searchRepository.save(goods);
    }

    @Test
    public void test() {
        boolean yitao = template.createIndex("yitao");
        Assert.assertTrue(yitao);
    }

}
