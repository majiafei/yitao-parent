package com.yitao.yitaodetailservice;

import com.yitao.common.utils.JsonUtils;
import com.yitao.detail.service.SpuDetailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @ProjectName: house
 * @Package: com.yitao.yitaodetailservice
 * @ClassName: SpuDetialServiceTest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/7/7 11:55
 */
public class SpuDetialServiceTest extends YitaoDetailServiceApplicationTests {

    @Autowired
    private SpuDetailService spuDetailService;

    @Test
    public void testdetail() {
        Map<String, Object> map = spuDetailService.loadModel(2L);
        System.out.println(JsonUtils.fromObjectToString(map));
    }

    @Test
    public void testCreateHtml() {
        spuDetailService.creaetStaticHtml(197L);
    }

}
