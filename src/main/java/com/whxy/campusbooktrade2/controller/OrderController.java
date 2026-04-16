package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 订单Controller，和UserController结构一致
 * 注：查询类接口用GET（符合RESTful规范），提交类接口用POST
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
     * 查询我的订单 - GET（查询接口用GET是规范，JWT拦截已放行/order/**）
     */
    @GetMapping("/my/{userId}")
    public R<List<OrderInfo>> getMyOrders(@PathVariable Long userId) {
        return orderService.getMyOrders(userId);
    }
}