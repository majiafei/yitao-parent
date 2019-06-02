package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.domain.Brand;
import com.yitao.dto.BrandDTO;
import com.yitao.entiry.PageResult;
import com.yitao.service.BrandService;
import com.yitao.vo.BrandVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.LongFunction;

/**
 * @ProjectName: house
 * @Package: com.yitao
 * @ClassName: BrandController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 21:36
 */

@Controller
@RequestMapping("/api/item/brand")
@Log4j2
public class BrandController {

    @Reference(check = false)
    private BrandService brandService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<BrandVO>> queryBrandByPage(BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.queryBrandByCondition(brandDTO));
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<BrandVO>> queryBrandListByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(brandService.queryBrandListByCid(cid));
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(BrandDTO brandDTO, @RequestParam("cids") List<Long> cids) {
        try {
            Brand brand = new Brand();
            brand.setBrandName(brandDTO.getName());
            brand.setBrandLetter(brandDTO.getLetter());
            brand.setBrandImage(brandDTO.getImage());

            brandService.saveBrand(brand, cids);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, List<Long> cids) {
        try {
            brandService.updateBrand(brand, cids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid) {
        try {
            brandService.deleteBrand(bid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bid/{bid}")
    public ResponseEntity<BrandVO> getBrandById(@PathVariable("bid") Long bid) {
        return ResponseEntity.ok(brandService.getBrandById(bid));
    }

}
