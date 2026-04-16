package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public R<User> login(String username, String password) {
        return userService.login(username, password);
    }

    // 注册接口
    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {
        // 设置默认值
        user.setCreateTime(new Date());
        user.setRole("user");
        // 保存到数据库
        boolean save = userService.save(user);
        if (save) {
            return R.ok("注册成功");
        } else {
            return R.fail("注册失败");
        }
    }
}