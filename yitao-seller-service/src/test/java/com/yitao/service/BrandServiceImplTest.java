package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.domain.Brand;
import com.yitao.dto.BrandDTO;
import com.yitao.entiry.PageResult;
import com.yitao.mapper.BrandMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: BrandServiceImplTest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 12:46
 */
public class BrandServiceImplTest extends YitaoSellerServiceApplicationTests {

    @Reference
    private BrandService brandService;

    @Autowired
    private BrandMapper brandMapper;

    @Test
    public void list() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setPage(0);
        brandDTO.setRows(10);
        PageResult<Brand> brandPageResult = brandService.queryBrandByCondition(brandDTO);
        Assert.assertEquals(10, brandPageResult.getRows().size());
    }

    @Test
    @Transactional
    public void testSaveCategoryBrand() {
        Brand brand = new Brand();
        brand.setBrandImage("https://img20.360buyimg.com/popshop/jfs/t2920/86/505601937/5265/e938b33e/575d1909N9af988df.jpg");
        brand.setBrandLetter("H");
        brand.setBrandName("鸿星尔克");

        Long[] cids = new Long[]{1121L};
        brandService.saveBrand(brand, Arrays.asList(cids));
    }

}
