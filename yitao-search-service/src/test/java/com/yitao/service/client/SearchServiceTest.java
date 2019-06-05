package com.yitao.service.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.Spu;
import com.yitao.search.service.SearchService;
import com.yitao.service.YitaoSearchServiceApplicationTests;
import com.yitao.vo.SpuVO;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

/**
 * @ClassName: SearchServiceTest
 * @Auther: admin
 * @Date: 2019/6/5 16:38
 * @Description:
 */
public class SearchServiceTest extends YitaoSearchServiceApplicationTests {

    @Reference
    private SearchService searchService;

    private static final String serverUrl = "http://api.yitao.com";

    @Test
    public void importGoods() {
        ResponseEntity<Spu> responseEntity = RestTemplateUtils.getForEntity(serverUrl + "/api/item/spu/{spuId}", Spu.class, 2L);
        Spu spu = responseEntity.getBody();
        searchService.importGoods(spu);
    }

}
