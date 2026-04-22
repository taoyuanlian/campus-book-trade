package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.BookService;
import com.whxy.campusbooktrade2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService; // 🔥 必须注入，用来查用户名

    @PostMapping("/add")
    public R<String> add(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // ====================== 🔥 核心修复：返回带 publisherName 的列表 ======================
    @GetMapping("/list")
    public R<List<Map<String, Object>>> list() {
        // 1. 查询所有书籍
        List<Book> bookList = bookService.list();

        // 2. 封装成带用户名的结构
        List<Map<String, Object>> result = new ArrayList<>();
        for (Book book : bookList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", book.getId());
            map.put("name", book.getName());
            map.put("author", book.getAuthor());
            map.put("price", book.getPrice());
            map.put("oldLevel", book.getOldLevel());
            map.put("userId", book.getUserId());

            // 🔥 关键：查出发布者用户名
            User user = userService.getById(book.getUserId());
            String publisherName = (user == null) ? "未知用户" : user.getUsername();
            map.put("publisherName", publisherName);

            result.add(map);
        }

        return R.ok(result);
    }

    @PutMapping("/update")
    public R<String> update(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}