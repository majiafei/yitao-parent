package com.yitao.yitaoorderservice.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yitao.domain.Sku;
import com.yitao.yitaoorderservice.YitaoOrderServiceApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Package: com.yitao.yitaoorderservice.client
 * @ClassName: SkuClientTest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 20:08
 */
public class SkuClientTest extends YitaoOrderServiceApplicationTests {

    @Autowired
    private SkuClient skuClient;

    @Test
    public void testGetSkuListr() {
        Set<Long> skuIds = Sets.newHashSet(2600242L);
        List<Sku> skuList = skuClient.getSkuList(skuIds);
        Assert.assertEquals(1, skuList.size());
    }

}
