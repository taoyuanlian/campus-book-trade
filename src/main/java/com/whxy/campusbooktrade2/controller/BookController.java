package com.whxy.campusbooktrade2.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public R<String> add(@RequestBody Book book) { return bookService.addBook(book); }
    @GetMapping("/list")
    public R<IPage<Book>> list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               String category, String name) {
        return R.ok(bookService.pageList(page, size, category, name));
    }
    @PutMapping("/update")
    public R<String> update(@RequestBody Book book) { return bookService.updateBook(book); }
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id) { return bookService.deleteBook(id); }
}