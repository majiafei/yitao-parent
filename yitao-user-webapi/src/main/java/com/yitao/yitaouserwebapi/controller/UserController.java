package com.yitao.yitaouserwebapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.domain.User;
import com.yitao.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: UserController
 * @Auther: admin
 * @Date: 2019/7/23 11:34
 * @Description:
 */
@Controller
@RequestMapping("/api/user/")
public class UserController {

    @Reference(check = false)
    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    @ResponseBody
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok( userService.checkData(data, type));
    }

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity send(String phone) {
        userService.sendCode(phone);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> register(User user, String code) {
        userService.register(user, code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/queryByUserName")
    @ResponseBody
    public ResponseEntity<User> queryByUserName(String userName) {
        User user = userService.queryByUserName(userName);
        return ResponseEntity.ok(user);
    }

}
