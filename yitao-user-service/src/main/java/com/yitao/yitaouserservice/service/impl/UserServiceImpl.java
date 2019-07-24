package com.yitao.yitaouserservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.common.exception.ParamValidationException;
import com.yitao.domain.User;
import com.yitao.mapper.UserMapper;
import com.yitao.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: UserServiceImpl
 * @Auther: admin
 * @Date: 2019/7/23 11:14
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUserName(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new ParamValidationException("参数非法");
        }
        return ( userMapper.selectCount(user) == 0);
    }
}
