package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.domain.SpecGroup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecGroupServiceTest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 11:37
 */
public class SpecGroupServiceTest extends YitaoSellerServiceApplicationTests {

    @Reference
    private SpecGroupService specGroupService;

    @Test
    public void testByCid() {
        List<SpecGroup> specGroups = specGroupService.querySpecGroupListByCid(76L);
        Assert.assertEquals(8, specGroups.size());
    }

    @Test
    @Transactional
    public void testSave() {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCategoryId(76L);
        specGroup.setSpectGroupName("76");
        specGroupService.saveSpecGroup(specGroup);
    }

    @Test
    @Transactional
    public void update() {
        SpecGroup specGroup = specGroupService.querySpecGroupByPrimaryKey(1L);
        specGroup.setSpectGroupName("1L");
        specGroupService.updateSpectGroup(specGroup);
    }

    @Test
    @Transactional
    public void testDelete() {
        specGroupService.deleteSpecGroup(1L);
    }

}
