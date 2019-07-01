package com.yitao.service.client;

import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.Spu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpuClient
 * @Auther: admin
 * @Date: 2019/7/1 18:24
 * @Description:
 */
@Component
public class SpuClient {
    @Value("${seller.server.url}")
    private String serverUrl;

    public Spu getSpuById(Long spuId) {
        String requestUrl = serverUrl + "/api/item/spu/{spuId}";
        ResponseEntity<Spu> responseEntity = RestTemplateUtils.getForEntity(requestUrl, Spu.class, spuId);
        return responseEntity.getBody();
    }
}
