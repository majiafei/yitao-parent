package com.yitao.service.client;

import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.vo.SpuDetailVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpuDetailClient
 * @Auther: admin
 * @Date: 2019/6/5 15:32
 * @Description:
 */

@Component
public class SpuDetailClient {

    @Value("${seller.server.url}")
    private String serverUrl;

    public SpuDetailVO getBySpuId(Long spuId) {
        String requestUrl = serverUrl + "/api/item/spu/detail/{spuId}";
        ResponseEntity<SpuDetailVO> responseEntity = RestTemplateUtils.getForEntity(requestUrl, SpuDetailVO.class, spuId);

        return responseEntity.getBody();
    }

}
