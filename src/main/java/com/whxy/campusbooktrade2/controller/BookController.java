package com.whxy.campusbooktrade2.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public R<String> add(@RequestBody Book book) { return bookService.addBook(book); }
    // 列表查询（修复泛型+返回书籍数组）
    @GetMapping("/list")
    public R<List<Book>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            String category,
            String name) {
        IPage<Book> bookPage = bookService.pageList(page, size, category, name);
        // 返回分页对象中的records（书籍数组），适配前端forEach
        return R.ok(bookPage.getRecords());
    }
    @PutMapping("/update")
    public R<String> update(@RequestBody Book book) { return bookService.updateBook(book); }
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id) { return bookService.deleteBook(id); }
}