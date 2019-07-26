package com.yitao.user;

import com.yitao.domain.User;

/**
 * @ClassName: service
 * @Auther: admin
 * @Date: 2019/7/23 11:12
 * @Description:
 */
public interface UserService{

    /**
     * 验证数据
     * @param data 要验证的数据
     * @param type 类型 1代表用户名，2代表手机号
     * @return
     */
    Boolean checkData(String data, Integer type);

    /**
     * 发送验证码
     * @param phone
     */
    void sendCode(String phone);

    /**
     * 注册用户
     * @param user 用户对象
     * @param code 验证码
     */
    void register(User user, String code);
}
