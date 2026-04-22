package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.service.OrderService;
import com.whxy.campusbooktrade2.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单Controller，调整返回值为OrderVo列表
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单（下单）- 纯POST，不兼容GET
     */
    @PostMapping("/create")
    public R<String> createOrder(@RequestBody OrderInfo orderInfo) {
        return orderService.createOrder(orderInfo);
    }

    /**
     * 查询我的订单 - GET（返回包含书名的OrderVo列表）
     */
    @GetMapping("/my/{userId}")
    public R<List<OrderVo>> getMyOrders(@PathVariable Long userId) {
        return orderService.getMyOrders(userId);
    }
}