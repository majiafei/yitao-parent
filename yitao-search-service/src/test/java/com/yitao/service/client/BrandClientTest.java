package com.yitao.service.client;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.yitao.common.utils.JsonUtils;
import com.yitao.service.YitaoSearchServiceApplicationTests;
import com.yitao.vo.BrandVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BrandClientTest
 * @Auther: admin
 * @Date: 2019/6/5 11:44
 * @Description:
 */
public class BrandClientTest extends YitaoSearchServiceApplicationTests {

    private static final String serverUrl = "http://api.yitao.com";

    @Autowired
    private BrandClient brandClient;

    @Test
    public void testGet1() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(serverUrl + "/api/item/brand/bid/1528");
        ResponseEntity<BrandVO> responseEntity = restTemplate.getForEntity(uri, BrandVO.class);
        Assert.assertEquals(1528L, responseEntity.getBody().getId().longValue());
    }

    @Test
    public void testGet2() {
        RestTemplate restTemplate = new RestTemplate();
        Map map = new HashMap();
        map.put("ids", Joiner.on(",").join(Lists.newArrayList(1528L, 1912L)));
        ResponseEntity forEntity = restTemplate.getForEntity(serverUrl + "/api/item/brand/list?ids={ids}", List.class, Joiner.on(",").join(Lists.newArrayList(1528L, 1912L)));
        String string = JsonUtils.fromObjectToString(forEntity.getBody());
        List<BrandVO> brandVOList = JsonUtils.fromJsonToList(string, BrandVO.class);

        System.out.println(brandVOList.size());
    }

    @Test
    public void testGetBrandById() {
        BrandVO brandVO = brandClient.getBrandById(1528L);
        Assert.assertEquals(1528L, brandVO.getId().longValue());
    }

}
