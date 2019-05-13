package com.yitao.service;

import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.domain.SpecParam;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecParamService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/10 21:45
 */
public class SpecParamServiceTest extends YitaoSellerServiceApplicationTests {

    @Autowired
    private SpecParamService specParamService;

    @Test
    public void testQuerySpecParamByCondition() {
/*        SpecParam specParam = new SpecParam();
        specParam.setCategoryId(76L);
        specParam.setSpecGroupId(2L);
        List<SpecParam> specParamList = specParamService.querySpecParamListByCondition(specParam);
//        Assert.assertEquals(18, specParamList.size());
        Assert.assertEquals(3L, specParamList.size());*/

        SpecParam specParam = new SpecParam();
        specParam.setCategoryId(76L);
        specParam.setSpecGroupId(100L);
        List<SpecParam> specParamList = specParamService.querySpecParamListByCondition(specParam);
        Assert.assertEquals(0L, specParamList.size());
    }


}
