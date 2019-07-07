package com.yitao.yitaodetailservice.client;

import com.google.common.base.Joiner;
import com.yitao.common.utils.JsonUtils;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: CategoryClient
 * @Auther: admin
 * @Date: 2019/6/5 15:04
 * @Description:
 */

@Component
public class CategoryClient {

    @Value("${seller.server.url}")
    private String serverUrl;

    public List<Category> getCategoryListByIds(List<Long> cids) {
        String requestUrl = serverUrl + "/api/item/category/listByCids?cids={cids}";
        ResponseEntity<List> responseEntity = RestTemplateUtils.getForEntity(requestUrl, List.class, Joiner.on(",").join(cids));
        // 将对象转换成json字符串
        String json = JsonUtils.fromObjectToString(responseEntity.getBody());
        // 将json转为list
        return JsonUtils.fromJsonToList(json, Category.class);
    }

}
