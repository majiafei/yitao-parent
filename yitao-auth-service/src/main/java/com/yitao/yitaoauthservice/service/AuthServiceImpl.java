package com.yitao.yitaoauthservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.auth.service.AuthService;
import com.yitao.common.entity.UserInfo;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.CodecUtils;
import com.yitao.common.utils.JwtUtils;
import com.yitao.domain.User;
import com.yitao.yitaoauthservice.client.UserClient;
import com.yitao.auth.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: AuthServiceImpl
 * @Auther: admin
 * @Date: 2019/7/26 11:11
 * @Description:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;

    @Override
    public String login(User user) {
        try {
            User userByName = userClient.getUserByUserName(user.getUsername());
            if (userByName == null) {
                throw new ServiceException("用户名或者密码错误");
            }

            // 判断密码是否正确
            String salt = userByName.getSalt();
            // 将数据库中的密码和盐进行加密
            String md5Pass = CodecUtils.md5Hex(userByName.getPassword(), salt);
            // 将用户输入的密码进行加密
            String mdfUserInputPass = CodecUtils.md5Hex(user.getPassword(), salt);
            if (!md5Pass.equals(mdfUserInputPass)) {
                throw new ServiceException("用户名或者密码错误");
            }

            // 生成token
            UserInfo userInfo = new UserInfo(userByName.getUserId(), userByName.getUsername());
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("用户名或者密码错误");
        }
    }
}
