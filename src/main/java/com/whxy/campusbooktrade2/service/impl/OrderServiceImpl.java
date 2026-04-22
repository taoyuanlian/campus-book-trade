package com.whxy.campusbooktrade2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.Book;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.mapper.OrderMapper;
import com.whxy.campusbooktrade2.service.BookService;
import com.whxy.campusbooktrade2.service.OrderService;
import com.whxy.campusbooktrade2.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderInfo> implements OrderService {

    // 🔥 注入 BookService 用来下架书籍
    @Autowired
    private BookService bookService;

    @Override
    public R<String> createOrder(OrderInfo orderInfo) {
        try {
            String orderNo = System.currentTimeMillis() + "" + new Random().nextInt(1000);
            orderInfo.setOrderNo(orderNo);
            orderInfo.setStatus(1);
            orderInfo.setCreateTime(new Date());

            boolean save = save(orderInfo);
            if (save) {
                // ==============================================
                // 🔥 🔥 核心：支付后 自动下架书籍
                // ==============================================
                Book book = bookService.getById(orderInfo.getBookId());
                if (book != null) {
                    book.setStatus(0); // 0=已下架
                    bookService.updateById(book);
                }

                return R.ok("下单成功！订单号：" + orderNo);
            } else {
                return R.fail("下单失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("系统异常");
        }
    }

    @Override
    public R<List<OrderVo>> getMyOrders(Long userId) {
        List<OrderVo> orderList = baseMapper.selectMyOrdersWithBookName(userId);
        return R.ok(orderList);
    }
}