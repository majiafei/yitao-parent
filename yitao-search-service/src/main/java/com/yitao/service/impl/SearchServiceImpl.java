package com.yitao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.JsonUtils;
import com.yitao.common.utils.NumberUtils;
import com.yitao.domain.Category;
import com.yitao.domain.Spu;
import com.yitao.search.model.Goods;
import com.yitao.search.service.SearchService;
import com.yitao.service.client.*;
import com.yitao.service.repository.SearchRepository;
import com.yitao.vo.BrandVO;
import com.yitao.vo.SkuVO;
import com.yitao.vo.SpecParamVO;
import com.yitao.vo.SpuDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String handleSegments(SpecParamVO specParamVO, double targetValue) {
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
    }
}
