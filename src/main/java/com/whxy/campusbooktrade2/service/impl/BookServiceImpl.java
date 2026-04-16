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
import java.util.Date;
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Override
    public R<String> addBook(Book book) {
        book.setCreateTime(new Date());
        book.setStatus(1);
        save(book);
        return R.ok("发布成功");
    }
    @Override
    public IPage<Book> pageList(int page, int size, String category, String name) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getStatus, 1);
        if(category!=null) wrapper.eq(Book::getCategory, category);
        if(name!=null) wrapper.like(Book::getName, name);
        return page(new Page<>(page, size), wrapper);
    }
    @Override
    public R<String> updateBook(Book book) {
        updateById(book);
        return R.ok("修改成功");
    }
    @Override
    public R<String> deleteBook(Long id) {
        removeById(id);
        return R.ok("删除成功");
    }
}