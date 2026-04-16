package com.whxy.campusbooktrade2.controller;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.BookService;
import com.whxy.campusbooktrade2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @GetMapping("/user/list")
    public R<List<User>> userList() { return R.ok(userService.list()); }
    @DeleteMapping("/book/{id}")
    public R<String> deleteBook(@PathVariable Long id) { return bookService.deleteBook(id); }
}