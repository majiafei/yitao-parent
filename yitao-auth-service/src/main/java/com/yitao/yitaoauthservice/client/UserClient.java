package com.yitao.yitaoauthservice.client;

import com.yitao.common.utils.RestTemplateUtils;
import com.yitao.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: UserClient
 * @Auther: admin
 * @Date: 2019/7/26 13:54
 * @Description:
 */
@Component
public class UserClient {
    @Value("${api.url}")
    private String url;

    public User getUserByUserName(String userName) {
        String requestUrl = url + "api/user/queryByUserName?userName={userName}";
        ResponseEntity<User> responseEntity = RestTemplateUtils.getForEntity(requestUrl, User.class, userName);
        return responseEntity.getBody();
    }
}
