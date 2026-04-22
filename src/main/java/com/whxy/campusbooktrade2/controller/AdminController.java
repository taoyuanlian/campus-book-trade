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

    @GetMapping("/user/list")
    public R<List<User>> userList() {
        if (!isAdmin()) {
            return R.fail("权限不足！仅管理员可查看所有用户");
        }
        return R.ok(userService.list());
    }

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

            // 🔥 根据 userId 查询用户名
            User user = userService.getById(book.getUserId());
            String publisherName = user == null ? "未知用户" : user.getUsername();
            map.put("publisherName", publisherName); // 前端要的就是这个！

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