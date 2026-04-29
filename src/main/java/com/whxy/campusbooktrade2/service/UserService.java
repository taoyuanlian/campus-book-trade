package com.whxy.campusbooktrade2.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.User;
public interface UserService extends IService<User> {
    R<User> login(String username, String password);
}