package com.yitao.service.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.yitao.common.utils.JsonUtils;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.Spu;
import com.yitao.entiry.PageResult;
import com.yitao.search.model.Goods;
import com.yitao.search.service.SearchService;
import com.yitao.service.YitaoSearchServiceApplicationTests;
import com.yitao.service.repository.SearchRepository;
import com.yitao.vo.SpuVO;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Goods goods = searchService.buildGoods(spu);
        searchRepository.save(goods);
    }

    @Test
    public void test() {
        boolean yitao = template.createIndex("goods");
        Assert.assertTrue(yitao);
    }

    @Test
    public void importAllGoods() {
        String api = "/api/item/spu/page?page={page}&rows={rows}&key={key}&saleable={saleable}";
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("rows", Integer.MAX_VALUE);
        params.put("key", "");
        params.put("saleable", true);
        ResponseEntity<PageResult> responseEntity = RestTemplateUtils.getForEntity(serverUrl + api, PageResult.class, params);
        System.out.println(responseEntity.getBody());
        List rows = responseEntity.getBody().getRows();
        String string = JsonUtils.fromObjectToString(rows);
        List<SpuVO> spuVOS = JsonUtils.fromJsonToList(string, SpuVO.class);
        Spu spu = null;
        for (SpuVO spuVO : spuVOS) {
           spu = RestTemplateUtils.getForEntity(serverUrl + "/api/item/spu/{spuId}", Spu.class, spuVO.getId()).getBody();
            Goods goods = searchService.buildGoods(spu);
            searchRepository.save(goods);
        }
    }

    @Test
    public void testMatch() {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("all", "小米");
        Iterable<Goods> search = searchRepository.search(queryBuilder);
        search.forEach(System.out::println);
    }

    @Test
    public void testNativeQuery(){
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("all", "小米"));

        int page = 0;
        int size = 10;
        searchQueryBuilder.withPageable(PageRequest.of(page, size));


        // 获取结果，默认分页，返回的是一个分页的对象
        Page<Goods> search = searchRepository.search(searchQueryBuilder.build());
        System.out.println(search.getTotalElements());
        System.out.println(search.getTotalPages());
        search.forEach(System.out::println);
    }

    @Test
    public void testSort() {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("all", "小米"));
        searchQueryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        Page<Goods> search = searchRepository.search(searchQueryBuilder.build());
        search.forEach(System.out::println);
    }

    /**
     * 聚合
     */
    @Test
    public void testAgg() {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withSourceFilter(new FetchSourceFilter(new  String[]{""}, null));
        searchQueryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brandId"));
        AggregatedPage<Goods> aggPage = (AggregatedPage<Goods>)searchRepository.search(searchQueryBuilder.build());
        // 取出名称为brands的聚合
        LongTerms agg = (LongTerms)aggPage.getAggregation("brands");
        List<LongTerms.Bucket> buckets = agg.getBuckets();
        for (LongTerms.Bucket bucket :buckets) {
            System.out.println(bucket.getDocCount());
            System.out.println(bucket.getKeyAsString());
        }
    }

}
