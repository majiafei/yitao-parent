package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.service.GoodsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: house
 * @Package: com.yitao.controller
 * @ClassName: GoodsController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 14:31
 */

@Controller
@RequestMapping("api/item")
public class GoodsController {

    @Reference(check = false)
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity querySpuByCondition(SpuDTO spuDTO) {
        return ResponseEntity.ok(goodsService.querySpuListByCondition(spuDTO));
    }

}
