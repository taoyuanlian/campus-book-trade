package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.BookService;
import com.whxy.campusbooktrade2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private HttpServletRequest request;

    private boolean isAdmin() {
        String role = (String) request.getAttribute("role");
        return "admin".equals(role);
    }

    // ========== 用户管理接口 ==========
    // 1. 查看所有用户列表
    @GetMapping("/user/list")
    public R<List<User>> userList() {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可查看所有用户");
        }
        return R.ok(userService.list());
    }

    // 2. 删除指定用户（新增！核心接口）
    @DeleteMapping("/user/{id}")
    public R<String> deleteUser(@PathVariable Long id) {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可删除用户");
        }
        try {
            // 安全防护：禁止删除admin自身
            User targetUser = userService.getById(id);
            if (targetUser != null && "admin".equals(targetUser.getUsername())) {
                return R.fail("禁止删除管理员账号！");
            }
            // 执行删除（复用MyBatis-Plus的removeById）
            boolean deleteSuccess = userService.removeById(id);
            if (deleteSuccess) {
                return R.ok("用户删除成功！");
            } else {
                return R.fail("删除失败：用户ID不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("删除失败：" + e.getMessage());
        }
    }

    // ========== 商品管理接口 ==========
    @GetMapping("/book/list")
    public R<List<Map<String, Object>>> bookList() {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可查看所有商品");
        }

        List<Book> bookList = bookService.list();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Book book : bookList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", book.getId());
            map.put("name", book.getName());
            map.put("author", book.getAuthor());
            map.put("userId", book.getUserId());

            // 返回发布者用户名
            User user = userService.getById(book.getUserId());
            String publisherName = user == null ? "未知用户" : user.getUsername();
            map.put("publisherName", publisherName);

            // 返回书籍状态
            map.put("status", book.getStatus());
            map.put("statusText", book.getStatus() == 1 ? "在售" : "已售出");

            result.add(map);
        }
        return R.ok(result);
    }

    @DeleteMapping("/book/{id}")
    public R<String> deleteBook(@PathVariable Long id) {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可删除任意商品");
        }
        return bookService.deleteBook(id);
    }
}