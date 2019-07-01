package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.Category;
import com.yitao.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.controller
 * @ClassName: CategoryController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 10:26
 */
@Controller
@RequestMapping("api/item/category")
@Log4j2
public class CategoryController {

    @Reference(check = false)
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));
    }

    @PostMapping("/save")
    public ResponseEntity saveCategory(Category category) {
        try {
            categoryService.saveCatetory(category);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity updateCategory(Category category) {
        try {
            categoryService.updateCategory(category);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable("categoryId") Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listByCids")
    public ResponseEntity<List<Category>> listByIds(@RequestParam("cids") List<Long> cids) {
        return ResponseEntity.ok(categoryService.listByIds(cids));
    }

    /**
     * 根据cid3查询三级分类
     * @param id
     * @return
     */
    @GetMapping("all/level/{id}")
    public ResponseEntity<List<Category>> queryAllByCid3(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.queryAllByCid3(id));
    }

}
