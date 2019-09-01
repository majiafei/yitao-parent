package com.yitao.yitaoauthservice.test;

import com.yitao.auth.service.AuthService;
import com.yitao.domain.User;
import com.yitao.yitaoauthservice.YitaoAuthServiceApplicationTests;
import com.yitao.yitaoauthservice.client.UserClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: AuthServiceTest
 * @Auther: admin
 * @Date: 2019/7/26 13:59
 * @Description:
 */
public class AuthServiceTest extends YitaoAuthServiceApplicationTests {
    @Autowired
    private UserClient userClient;

    @Test
    public void testLogin() {
        User admin = userClient.getUserByUserName("admin");
        Assert.assertNotNull(admin);
    }

}
