package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.domain.Sku;
import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.service.GoodsService;
import com.yitao.vo.SkuVO;
import com.yitao.vo.SpuDetailVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.saveGoods(spuDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetailVO> getSpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        return ResponseEntity.ok(goodsService.getSpuDetailBySpuId(spuId));
    }

    @GetMapping("/sku/list")
    public ResponseEntity<List<SkuVO>> getSkuListBySpuId(@RequestParam("id") Long spuId) {
        return ResponseEntity.ok(goodsService.getSkuListBySpuId(spuId));
    }

    @DeleteMapping("/spu/spuId/{spuId}")
    public ResponseEntity<Void> deleteGoodsById(@PathVariable("spuId") Long spuId) {
        goodsService.deleteGoodsById(spuId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/spu/saleable")
    public ResponseEntity<Void> saleableSpu(@RequestBody SpuDTO spuDTO) {
        goodsService.saleableSpu(spuDTO);
        return ResponseEntity.ok().build();
    }

}
