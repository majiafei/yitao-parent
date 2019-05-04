package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.domain.Category;
import org.junit.Assert;
import org.junit.Test;

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

}
