package com.yitao.service.client;

import com.yitao.service.YitaoSearchServiceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: SkuClientTest
 * @Auther: admin
 * @Date: 2019/6/10 11:54
 * @Description:
 */
public class SkuClientTest extends YitaoSearchServiceApplicationTests {

    @Autowired
    private SkuClient skuClient;

    @Test
    public void testGetSkuList() {
        skuClient.getSkuVOListBySpuId(2L);
    }

}
