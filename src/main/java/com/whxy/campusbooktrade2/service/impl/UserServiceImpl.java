package com.whxy.campusbooktrade2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.mapper.UserMapper;
import com.whxy.campusbooktrade2.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public R<User> login(String username, String password) {
        // 根据用户名查询用户
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            return R.fail("用户不存在");
        }
        // 校验密码（期末作业简化，正式项目要加密）
        if (!user.getPassword().equals(password)) {
            return R.fail("密码错误");
        }
        return R.ok(user);
    }
}