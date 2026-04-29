package com.whxy.campusbooktrade2.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.mapper.BookMapper;
import com.whxy.campusbooktrade2.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    /**
     * 获取当前登录用户的userId（从request上下文）
     */
    private Long getCurrentUserId() {
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (Long) request.getAttribute("userId");
    }
    /**
     * 获取当前登录用户的role
     */
    private String getCurrentRole() {
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (String) request.getAttribute("role");
    }
    @Override
    public R<String> addBook(Book book) {
        book.setCreateTime(new Date());book.setStatus(1);
        book.setUserId(getCurrentUserId());save(book); // 关联发布者ID
        return R.ok("发布成功");
    }
    @Override
    public IPage<Book> pageList(int page, int size, String category, String name) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getStatus, 1);
        // 修复：增加非空+非空字符串判断
        if (StringUtils.hasText(category)) {wrapper.eq(Book::getCategory, category);}
        if (StringUtils.hasText(name)) {wrapper.like(Book::getName, name);}
        return page(new Page<>(page, size), wrapper);
    }
    @Override
    public R<String> updateBook(Book book) {
        // 普通用户仅能修改自己的商品，管理员可修改任意商品
        if (!"admin".equals(getCurrentRole())) {
            Book oldBook = getById(book.getId());
            if (oldBook == null) {return R.fail("商品不存在");}
            if (!oldBook.getUserId().equals(getCurrentUserId())) {return R.fail("权限不足！仅能修改自己发布的商品");}
        }
        updateById(book);
        return R.ok("修改成功");
    }
    @Override
    public R<String> deleteBook(Long id) {Book book = getById(id);
        if (book == null) {return R.fail("商品不存在");}
        // 管理员：直接删除任意商品
        if ("admin".equals(getCurrentRole())) {removeById(id);
            return R.ok("删除成功");
        }
        // 普通用户：仅能删除自己发布的商品
        if (!book.getUserId().equals(getCurrentUserId())) {return R.fail("权限不足！仅能删除自己发布的商品");}
        removeById(id);
        return R.ok("删除成功");
    }
}
