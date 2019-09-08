package com.yitao.yitaoorderservice.client;

import com.google.common.base.Joiner;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.Sku;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: yitao
 * @Package: com.yitao.yitaoorderservice.client
 * @ClassName: SkuClient
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 19:57
 */
@Component
public class SkuClient {

    @Value("${seller.server.url}")
    private String serverUrl;

    public List<Sku> getSkuList(Set<Long> skuIdList) {
        String url = serverUrl + "/api/item/sku/skuListByIds?skuIds={skuIds}";
        ResponseEntity<List> entity = RestTemplateUtils.getForEntity(url, List.class, Joiner.on(",").join(skuIdList));

        return entity.getBody();
    }
}
