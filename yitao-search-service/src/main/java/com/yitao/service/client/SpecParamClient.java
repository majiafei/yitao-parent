package com.yitao.service.client;

import com.yitao.common.utils.JsonUtils;
import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.vo.SpecParamVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: SpecParamClient
 * @Auther: admin
 * @Date: 2019/6/5 16:09
 * @Description:
 */

@Component
public class SpecParamClient {

    @Value("${seller.server.url}")
    private String serverUrl;

    public List<SpecParamVO> getSpecParamListByCid(Long cid) {
        String requestUrl = serverUrl + "/api/item/spec/params?cid={cid}";
        ResponseEntity<List> responseEntity = RestTemplateUtils.getForEntity(requestUrl, List.class, cid);
        String json = JsonUtils.fromObjectToString(responseEntity.getBody());
        List<SpecParamVO> paramVOList = JsonUtils.fromJsonToList(json, SpecParamVO.class);
        return paramVOList;
    }

}
