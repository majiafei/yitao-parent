package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.domain.Category;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
     * @ProjectName: house
     * @Package: com.yitao.service
     * @ClassName: CategoryServiceImplTest
     * @Author: majiafei
     * @Description:
     * @Date: 2019/5/4 18:53
     */
    public class CategoryServiceImplTest extends YitaoSellerServiceApplicationTests {

        @Reference
        private CategoryService categoryService;

    @Test
    public void testQueryBrandListByBid() {
        List<Category> queryCategoryListByBid = categoryService.queryCategoryListByBid(325402L);
        Assert.assertEquals(4, queryCategoryListByBid.size());
    }

    @Test
    @Transactional
    public void testSaveCategory() {
        Category category = new Category();
        category.setName("工业品");
        category.setIsParent(true);
        category.setParentId(0L);
        category.setSort(1);

        categoryService.saveCatetory(category);
    }

    @Test
    @Transactional
    public void testUpdateCategory() {
        Category category = categoryService.selectByPrimaryKey(1L);
        category.setName("000001");
        categoryService.updateCategory(category);
    }

    @Test
    @Transactional
    public void testDeleteCategory() {
        categoryService.deleteCategoryById(1L);
    }

}
