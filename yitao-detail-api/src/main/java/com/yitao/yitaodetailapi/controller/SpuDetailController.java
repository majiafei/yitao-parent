package com.yitao.yitaodetailapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.detail.service.SpuDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Package: com.yitao.yitaodetailapi.controller
 * @ClassName: SpuDetailController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/7/7 12:40
 */
@Controller
public class SpuDetailController {

    @Reference
    private SpuDetailService spuDetailService;

    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long spuId, Model model) {
        Map<String, Object> attributes = spuDetailService.loadModel(spuId);
        model.addAllAttributes(attributes);
        // 同步
        //detailService.createHtml(spuId);
        // 异步生成静态详情页
//        detailService.asyncExecute(spuId);
        spuDetailService.creaetStaticHtml(spuId);

        return "item";
    }

}
