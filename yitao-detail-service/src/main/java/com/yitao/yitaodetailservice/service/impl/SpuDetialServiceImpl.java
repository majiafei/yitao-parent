package com.yitao.yitaodetailservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yitao.common.exception.ServiceException;
import com.yitao.detail.service.SpuDetailService;
import com.yitao.domain.Category;
import com.yitao.domain.Spu;
import com.yitao.domain.SpuDetail;
import com.yitao.vo.BrandVO;
import com.yitao.vo.SkuVO;
import com.yitao.vo.SpecParamVO;
import com.yitao.vo.SpuDetailVO;
import com.yitao.yitaodetailservice.client.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: house
 * @Package: com.yitao.yitaodetailservice.service.impl
 * @ClassName: SpuDetialServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/7/7 11:43
 */
@Service
@Log4j2
public class SpuDetialServiceImpl implements SpuDetailService {

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpuDetailClient spuDetailClient;

    @Autowired
    private SpecParamClient specParamClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${static.detail.path}")
    private String staticHtmlPath;

    @Override
    public Map<String, Object> loadModel(Long spuId) {
        if (spuId == null) {
            throw new ServiceException("请选择一款商品");
        }

        Spu spu = spuClient.getSpuById(spuId);
        List<SkuVO> skuVOList = skuClient.getSkuVOListBySpuId(spuId);
        BrandVO brandVO = brandClient.getBrandById(spu.getBrandId());
        List<Category> categoryListByIds = categoryClient.getCategoryListByIds(Lists.newArrayList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        SpuDetailVO spuDetailVO = spuDetailClient.getBySpuId(spuId);
        List<SpecParamVO> specParamListByCid = specParamClient.getSpecParamListByCid(spu.getCid3());

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("spu", spu);
        resultMap.put("skus", skuVOList);
        resultMap.put("brand", brandVO);
        resultMap.put("categories", categoryListByIds);
        resultMap.put("detail", spuDetailVO);
        resultMap.put("specs", specParamListByCid);

        return resultMap;
    }

    @Override
    public void creaetStaticHtml(Long spuId) {
        Context context = new Context();
        context.setVariables(loadModel(spuId));

        File file = new File(staticHtmlPath, spuId + ".html");
        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter printWriter = new PrintWriter(file, "utf-8")) {
            templateEngine.process("item", context, printWriter);
        } catch (Exception e) {
            log.error("生成静态详情页面失败", e);
            deleteHtml(spuId);
        }
    }

    @Override
    public void deleteHtml(Long spuId) {
        File file = new File(staticHtmlPath, spuId +".html");
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                log.error("删除静态页面失败， spuId=" + spuId);
            }
        }
    }
}
