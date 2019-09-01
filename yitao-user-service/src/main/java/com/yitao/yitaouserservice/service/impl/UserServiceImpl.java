package com.yitao.yitaouserservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.yitao.common.exception.ParamValidationException;
import com.yitao.common.exception.ServiceException;
import com.yitao.common.utils.CodecUtils;
import com.yitao.common.utils.NumberUtils;
import com.yitao.domain.User;
import com.yitao.mapper.UserMapper;
import com.yitao.user.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String VERIFY_CODE_PREFIX = "user:verify:code:";

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new ParamValidationException("参数非法");
        }
        return ( userMapper.selectCount(user) == 0);
    }

    @Override
    public void sendCode(String phone) {
        // 生成短信验证码
        String code = NumberUtils.generateCode(6);
        String key = VERIFY_CODE_PREFIX + phone;

        // 将验证码存放到redis中
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        Map<String, String> map = Maps.newHashMap();
        map.put("phone", phone);
        map.put("code", code);
        // 异步发送验证码
        amqpTemplate.convertAndSend("yt.sms.exchange", "sms.verify.code", map);
    }

    @Override
    public void register(User user, String code) {
        // 1.获取验证从redis中
        String key = VERIFY_CODE_PREFIX + user.getPhone();
        String codeFromRedis = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(codeFromRedis)) {
            throw new ServiceException("验证码已经过期");
        }
        // 2.比较验证码
        if (!codeFromRedis.equals(code)) {
            throw new ServiceException("验证码错误");
        }
        // 生成盐
        String salt = CodecUtils.generateSalt();
        // 将密码加密
        String password = CodecUtils.md5Hex(user.getPassword(), salt);
        user.setPassword(password);
        user.setCreated(new Date());
        user.setSalt(salt);
        // 将用户信息保存到数据库
        int insert = userMapper.insert(user);
        if (insert == 0) {
            throw new ServiceException("注册失败");
        }
        // 将验证码从redis中删除
        stringRedisTemplate.delete(key);
    }

    @Override
    public User queryByUserName(String userName) {
        User user = new User();
        user.setUsername(userName);
        return userMapper.selectOne(user);
    }
}
