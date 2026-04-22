package com.whxy.campusbooktrade2.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;

public interface BookService extends IService<Book> {
    R<String> addBook(Book book);

    IPage<Book> pageList(int page, int size, String category, String name);

    R<String> updateBook(Book book);
    R<String> deleteBook(Long id);
}