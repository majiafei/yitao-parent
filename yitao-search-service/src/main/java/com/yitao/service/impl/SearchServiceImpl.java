package com.yitao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sun.xml.internal.bind.v2.TODO;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.JsonUtils;
import com.yitao.common.utils.NumberUtils;
import com.yitao.domain.Brand;
import com.yitao.domain.Category;
import com.yitao.domain.SpecParam;
import com.yitao.domain.Spu;
import com.yitao.search.model.Goods;
import com.yitao.search.model.SearchRequest;
import com.yitao.search.model.SearchResult;
import com.yitao.search.service.SearchService;
import com.yitao.service.client.*;
import com.yitao.service.repository.SearchRepository;
import com.yitao.vo.BrandVO;
import com.yitao.vo.SkuVO;
import com.yitao.vo.SpecParamVO;
import com.yitao.vo.SpuDetailVO;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: SearchServiceImpl
 * @Auther: admin
 * @Date: 2019/6/5 11:32
 * @Description:
 */

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpuDetailClient spuDetailClient;

    @Autowired
    private SpecParamClient specParamClient;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ElasticsearchTemplate template;

    private static final String BRAND_AGG_NAME = "brandAgg";

    private static final String CATEGORY_AGG_NAME = "categoryAgg";

    @Override
    public Goods buildGoods(Spu spu) {
        // 品牌
        BrandVO brandVO = brandClient.getBrandById(spu.getBrandId());
        if (brandVO == null) {
            throw new ServiceException("品牌不存在, id = " + spu.getBrandId());
        }

        // 类目
        List<Category> categoryListByIds = categoryClient.getCategoryListByIds(Lists.newArrayList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categoryListByIds)) {
            throw new ServiceException("查不到类目");
        }
        List<String> categoryNameList = categoryListByIds.stream().map(Category::getName).collect(Collectors.toList());

        // 组合title，brandname，categoryname
       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append(Joiner.on(" ").join(categoryNameList));
       stringBuilder.append(spu.getTitle());
       stringBuilder.append(brandVO.getName());

       // 规格参数
        SpuDetailVO spuDetailVO = spuDetailClient.getBySpuId(spu.getSpuId());

        // 通用规格参数
        String genericSpec = spuDetailVO.getGenericSpec();
        // 将通用的谷歌参数json转换为map对象
        Map<Long, String> genericaParamsMap = JsonUtils.jsonToMap(genericSpec, Long.class, String.class);
        if (genericaParamsMap == null) {
            throw new ServiceException("parse failed");
        }

        // 特殊规格参数
        String specialSpec = spuDetailVO.getSpecialSpec();
        Map<Long, List<String>> specialSpecMap = JsonUtils.readValue(specialSpec, new TypeReference<Map<Long, List<String>>>() {});
        if (specialSpecMap == null) {
            throw new ServiceException("parse failed");
        }

        // 查询类目下所有的规格参数
        List<SpecParamVO> specParamVOList = specParamClient.getSpecParamListByCid(spu.getCid3());
        // 处理规格参数
        Map<String, Object> specParamsMap = handleSpecParams(specParamVOList, genericaParamsMap, specialSpecMap);

        // 查询sku信息
        List<SkuVO> skuVOListBySpuId = skuClient.getSkuVOListBySpuId(spu.getSpuId());
        if (CollectionUtils.isEmpty(skuVOListBySpuId)) {
            throw new ServiceException("not found sku info");
        }
        Set<Double> priceSet = Sets.newTreeSet();
            skuVOListBySpuId.forEach(skuVO -> {
            priceSet.add(skuVO.getPrice());
        });

        // 设置id
        Goods goods = new Goods();
        goods.setId(spu.getSpuId());
        goods.setTitle(spu.getTitle());
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setAll(stringBuilder.toString());
        goods.setSpecParams(specParamsMap);
        goods.setSkus(JsonUtils.fromObjectToString(skuVOListBySpuId));
        goods.setCreateTime(new Date());
        goods.setPriceSet(priceSet);

        return goods;
    }

    @Override
    public SearchResult<Goods> search(SearchRequest searchRequest) {
        // 根据关键词搜索
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 需要分页
        queryBuilder.withPageable(PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize()));
        // 查询做字段的帅选
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subtitle","skus"}, null));
        // 设置搜索条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", searchRequest.getKeywords()).operator(Operator.AND));

        // 品牌聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(BRAND_AGG_NAME).field("brandId"));
        // 分类聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(CATEGORY_AGG_NAME).field("cid3"));

        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);

        // 获取查询的内容
        List<Goods> content = result.getContent();

        // 获取聚合结果
        Aggregations aggregations = result.getAggregations();

        // 解析品牌聚合
        List<BrandVO> brandVOList = handleBrandAgg(aggregations.get(BRAND_AGG_NAME));

        // 解析分类聚合
        List<Category> categoryList = handleCategoryAg(aggregations.get(CATEGORY_AGG_NAME));

        //对规格参数聚合
        List<Map<String, Object>> specs = null;
        if (categoryList != null && categoryList.size() == 1) {
            specs = handleSpecs(categoryList.get(0).getId(), queryBuilder);
        }

        return SearchResult.build(result.getTotalElements(), result.getTotalPages(),content,brandVOList, categoryList, specs); // TODO
    }

    private List<Category> handleCategoryAg(LongTerms longTerms) {
        List<Long> cids = longTerms.getBuckets()
                .stream()
                .map(bucket ->
                    bucket.getKeyAsNumber().longValue()).
                        collect(Collectors.toList());
        return categoryClient.getCategoryListByIds(cids);
    }

    private List<BrandVO> handleBrandAgg(LongTerms longTerms){
        // 获取聚合后的所有的brandId
        List<Long> brandIds = longTerms.getBuckets()
                .stream()
                .map(bucket -> bucket.getKeyAsNumber().longValue())
                .collect(Collectors.toList());

        List<BrandVO> brandVOList = Lists.newArrayList();
       for (Long brandId : brandIds) {
           brandVOList.add(brandClient.getBrandById(brandId));
       }
        return brandVOList;
    }

    /**
     * 对规格参数进行聚合并解析结果
     *
     * @param id
     * @param basicQuery
     * @return
     */
    private List<Map<String, Object>> handleSpecs(Long id,  NativeSearchQueryBuilder basicQuery) {
        List<Map<String, Object>> specs = new ArrayList<>();

        //查询可过滤的规格参数
        List<SpecParamVO> params = specParamClient.getSpecParamListByCid(id);

        //基本查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withPageable(PageRequest.of(0, 1));

        for (SpecParamVO param : params) {
            if (param.getIsSearching()) {
                //聚合
                String name = param.getName();
                // 如果对keyword类型的字符串进行搜索必须是精确匹配terms
                queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specParams." + name + ".keyword"));
            }
        }
        //查询
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);

        //对聚合结果进行解析
        Aggregations aggs = result.getAggregations();
        for (SpecParamVO param : params) {
            if (param.getIsSearching()) {
                String name = param.getName();
                Terms terms = aggs.get(name);
                //创建聚合结果
                HashMap<String, Object> map = new HashMap<>();
                map.put("k", name);
                List<String> options = Lists.newArrayList();
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    if (StringUtils.hasText(bucket.getKeyAsString())) {
                        options.add(bucket.getKeyAsString());
                    }
                }
                map.put("options", options);
            /*    map.put("options", terms.getBuckets()
                        .stream()
                        .map(b -> ((Terms.Bucket) b).getKey())
                        .collect(Collectors.toList()));*/
                specs.add(map);
            }
        }
        return specs;
    }

    /**
     * 处理规格参数
     * @param specParamVOList 规格参数list
     * @param genericaParamsMap 通用规格参数map
     * @param specialSpecMap 特殊规格参数map
     * @return
     */
    private Map<String, Object> handleSpecParams(List<SpecParamVO> specParamVOList, Map<Long, String> genericaParamsMap, Map<Long, List<String>> specialSpecMap) {
        Map<String, Object> specParamsMap = Maps.newHashMap();
        for (SpecParamVO specParamVO : specParamVOList) {
            String valueResult = "";
            Long id = specParamVO.getId();
            String name = specParamVO.getName();
            if (specParamVO.getGeneric() == 1) {
                String genericValue = genericaParamsMap.get(id);
                // 单位
                String unit = specParamVO.getUnit() != null ? specParamVO.getUnit() : "";
                // 数值类型的
                if (specParamVO.getNumeric() != null && specParamVO.getNumeric() == 1) {
                    double targetValue = NumberUtils.toDouble(genericValue);
                    // 有分段
                    if (StringUtils.hasText(specParamVO.getSegments())) {
                        valueResult = handleSegments(specParamVO, targetValue);
                    } else {
                        valueResult = genericValue + unit;
                    }
                } else {
                    valueResult = genericValue;
                }
                specParamsMap.put(name, valueResult);
            } else {
                specParamsMap.put(name, specialSpecMap.get(id));
            }
        }

        return specParamsMap;
    }

   /* private String handleSegments(SpecParamVO specParamVO, double targetValue) {
        String valueResult = "";
        String segments = specParamVO.getSegments();
        String[] segmentsArray = StringUtils.tokenizeToStringArray(segments, "-");
        Set<Double> set = new TreeSet<>();

        try {
            for (String segment : segmentsArray) {
                String[] numbers = StringUtils.tokenizeToStringArray(segment, ",");
                for (String number : numbers) {
                    set.add(NumberUtils.toDouble(number));
                }
            }
            // 分段中所有数值的集合
            List<Double> numberList = Lists.newArrayList(set);

            String unit = specParamVO.getUnit() != null ? specParamVO.getUnit() : "";

            if (!CollectionUtils.isEmpty(numberList)) {
                if (numberList.get(numberList.size() - 1) <= targetValue) {
                    valueResult = numberList.get(numberList.size() - 1) + "以上";
                }

                if (numberList.get(0) > targetValue) {
                    valueResult = numberList.get(0) + unit + "以下";
                }

                if (!StringUtils.hasText(valueResult)) {
                    for (int i = 1; i < numberList.size(); i++) {
                        if (targetValue < numberList.get(i)) {
                            if (i - 1 == 0) {
                                valueResult = numberList.get(i - 1) + unit + "以下";
                            } else {
                                valueResult = numberList.get(i - 1) + "-" + numberList.get(i) + unit;
                            }
                            break;
                        }
                    }
                }
            }
            return valueResult;
        } catch (Exception e) {
            throw new ServiceException("处理分段失败");
        }
    }*/
    private String handleSegments(SpecParamVO specParamVO, double targetValue) {
        String result = "其他";
        String segments = specParamVO.getSegments();
        String[] segmentArray = StringUtils.tokenizeToStringArray(segments, ",");
        for (String segment : segmentArray) {
            String[] values = StringUtils.tokenizeToStringArray(segment, "-");
            double begin = NumberUtils.toDouble(values[0]);
            double end = Double.MAX_VALUE;
            if (values.length == 2) {
                end = NumberUtils.toDouble(values[1]);
            }

            // 比較
            if (targetValue >= begin && targetValue < end) {
                if (values.length == 1) {
                    result = begin + specParamVO.getUnit() +  "及以上";
                } else if (begin == 0) {
                    result = end + specParamVO.getUnit() + "及以下";
                } else {
                    result = begin + "-" + end + specParamVO.getUnit();
                }
                break;
            }
        }

        return result;
    }
}
