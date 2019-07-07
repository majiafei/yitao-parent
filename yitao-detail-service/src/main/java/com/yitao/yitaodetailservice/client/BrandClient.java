package com.yitao.yitaodetailservice.client;

import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.vo.BrandVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @ClassName: BrandClient
 * @Auther: admin
 * @Date: 2019/6/5 14:31
 * @Description:
 */

@Component
public class BrandClient {

    @Value("${seller.server.url}")
    private String serverUrl;

    public BrandVO getBrandById(Long brandId) {
        String requestUrl = serverUrl + "/api/item/brand/bid/{bid}";
        ResponseEntity<BrandVO> forEntity = RestTemplateUtils.getForEntity(requestUrl, BrandVO.class, brandId);

        return forEntity.getBody();
    }

}
