package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.UserService;
import com.whxy.campusbooktrade2.util.JwtUtil; // 必须导入
import org.springframework.beans.factory.annotation.Autowired; // 必须导入
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    // 登录接口
    @PostMapping("/login")
    public R<String> login(String username, String password) {
        // 修复原因1：先接收R<User>类型的返回值（和UserService的login方法匹配）
        R<User> loginResponse = userService.login(username, password);

        // 第一步：判断登录是否失败（比如用户不存在/密码错误）
        if (loginResponse.getCode() != 200) {
            return R.fail(loginResponse.getMsg()); // 直接返回失败信息
        }

        // 第二步：从R对象中取出真正的User数据（这才是你要的用户信息）
        User user = loginResponse.getData();

        // 第三步：生成JWT Token（现在jwtUtil有值，不会空指针）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        String token = jwtUtil.generateToken(claims);

        // 第四步：返回Token（符合接口返回规范）
        return R.ok(token);
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