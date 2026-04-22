package com.whxy.campusbooktrade2.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    private UserService userService;

    /**
     * 发布书籍（适配Service层的addBook方法，自动关联当前用户）
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    /**
     * 分页查询书籍（支持分类、关键词搜索）
     * 修复点：使用Page实现类，而非直接实现IPage接口
     */
    @GetMapping("/list")
    public R<IPage<Map<String, Object>>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name
    ) {
        // 调用Service的分页查询方法
        IPage<Book> bookPage = bookService.pageList(page, size, category, name);

        // 修复：使用Page实现类，避免匿名实现类的语法错误
        IPage<Map<String, Object>> resultPage = new Page<>();
        resultPage.setCurrent(bookPage.getCurrent());
        resultPage.setSize(bookPage.getSize());
        resultPage.setTotal(bookPage.getTotal());
        resultPage.setPages(bookPage.getPages());

        // 组装返回数据（补充发布者名称，修复空指针）
        List<Map<String, Object>> records = new ArrayList<>();
        for (Book book : bookPage.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", book.getId());
            map.put("name", book.getName());
            map.put("author", book.getAuthor());
            map.put("price", book.getPrice());
            map.put("oldLevel", book.getOldLevel());
            map.put("category", book.getCategory());
            map.put("userId", book.getUserId());
            map.put("createTime", book.getCreateTime());

            // 修复：空指针防护
            User user = userService.getById(book.getUserId());
            map.put("publisherName", user != null ? user.getUsername() : "未知用户");
            records.add(map);
        }
        resultPage.setRecords(records);

        return R.ok(resultPage);
    }

    /**
     * 获取所有书籍分类（供前端下拉框使用）
     */
    /**
     * 获取所有书籍分类（供前端下拉框使用）
     */
    @GetMapping("/categories")
    public R<List<String>> getCategories() {
        // 修复：替换List.of()为new ArrayList()（兼容所有JDK版本）
        List<String> categories = new ArrayList<>();
        categories.add("编程");
        categories.add("文学");
        categories.add("教材");
        categories.add("小说");
        categories.add("社科");
        categories.add("其他");
        return R.ok(categories);
    }
    /**
     * 修改书籍（Service层已做权限控制）
     */
    @PutMapping("/update")
    public R<String> update(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    /**
     * 删除书籍（Service层已做权限控制）
     */
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}