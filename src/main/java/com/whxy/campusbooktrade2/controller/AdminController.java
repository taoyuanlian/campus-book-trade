package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.BookService;
import com.whxy.campusbooktrade2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 权限校验工具方法：判断是否为管理员
     */
    private boolean isAdmin() {
        String role = (String) request.getAttribute("role");
        return "admin".equals(role);
    }

    // 1. 管理员查看所有用户（需确保UserService继承IService<User>）
    @GetMapping("/user/list")
    public R<List<User>> userList() {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可查看所有用户");
        }
        return R.ok(userService.list());
    }

    // 2. 管理员查看所有书籍（关键修正：通过BookServiceImpl的MP能力查询）
    @GetMapping("/book/list")
    public R<List<Book>> bookList() {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可查看所有商品");
        }
        // 核心修正：BookServiceImpl继承了ServiceImpl，可通过注入实现类调用getBaseMapper()
        // 方式1：直接注入BookServiceImpl（推荐，简单直接）
        // return R.ok(bookServiceImpl.getBaseMapper().selectList(null));

        // 方式2：若不想注入实现类，在BookService新增listAll()方法（更规范）
        return R.ok(((com.whxy.campusbooktrade2.service.impl.BookServiceImpl) bookService).getBaseMapper().selectList(null));
    }

    // 3. 管理员强制删除任意商品（复用你已实现的deleteBook方法，自带权限逻辑）
    @DeleteMapping("/book/{id}")
    public R<String> deleteBook(@PathVariable Long id) {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可删除任意商品");
        }
        // 直接调用BookService的deleteBook方法，复用原有权限+删除逻辑
        return bookService.deleteBook(id);
    }
}