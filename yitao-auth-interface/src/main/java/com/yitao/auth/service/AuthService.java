package com.yitao.auth.service;

import com.yitao.domain.User;

/**
 * @ClassName: AuthService
 * @Auther: admin
 * @Date: 2019/7/26 11:06
 * @Description:
 */
public interface AuthService {

    /**
     * 用户登录的方法
     * @param user
     * @return
     */
    String login(User user);

}
