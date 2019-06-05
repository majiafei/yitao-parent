package com.yitao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.JsonUtils;
import com.yitao.domain.Category;
import com.yitao.domain.Spu;
import com.yitao.search.model.Goods;
import com.yitao.search.service.SearchService;
import com.yitao.service.client.BrandClient;
import com.yitao.service.client.CategoryClient;
import com.yitao.service.client.SpecParamClient;
import com.yitao.service.client.SpuDetailClient;
import com.yitao.vo.BrandVO;
import com.yitao.vo.SpecParamVO;
import com.yitao.vo.SpuDetailVO;
import com.yitao.vo.SpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
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

    @Override
    public void importGoods(Spu spu) {
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
        Map<String, Object> specParamsMap = Maps.newHashMap();
        for (SpecParamVO specParamVO : specParamVOList) {
            Long id = specParamVO.getId();
            String name = specParamVO.getName();
            if (specParamVO.getGeneric() == 1) {
                String genericValue = genericaParamsMap.get(id);
                specParamsMap.put(name, genericValue);
            } else {
                List<String> specicValueList = specialSpecMap.get(id);
                specParamsMap.put(name, specicValueList);
            }
        }

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
    }
}
