package com.yitao.yitaodetailservice.client;

import com.yitao.common.utils.JsonUtils;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.vo.SkuVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: SkuClient
 * @Auther: majiafei
 * @Date: 2019/6/10 10:54
 * @Description:
 */

@Component
public class SkuClient {

    @Value("${seller.server.url}")
    private String serverUrl;

    public List<SkuVO> getSkuVOListBySpuId(Long spuId) {
        String requestUrl = serverUrl + "/api/item/sku/list?id={spuId}";

        ResponseEntity<List> responseEntity = RestTemplateUtils.getForEntity(requestUrl, List.class, spuId);

        List<SkuVO> skuVOList = JsonUtils.fromJsonToList(JsonUtils.fromObjectToString(responseEntity.getBody()), SkuVO.class);

        return skuVOList;
    }

}
