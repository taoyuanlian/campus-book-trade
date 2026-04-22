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

    /**
     * 新增：修改订单状态（标记为已支付/已取消）
     * @param orderId 订单ID
     * @param status 要修改的状态（1=待支付 2=已支付 3=已取消）
     * @param token JWT令牌（和其他接口保持一致，校验登录状态）
     * @return 操作结果
     */
    @PutMapping("/updateStatus/{orderId}")
    public R<String> updateOrderStatus(
            @PathVariable Long orderId,       // 订单ID（从URL路径获取）
            @RequestParam Integer status,     // 要修改的状态（从请求参数获取）
            @RequestHeader(required = false) String token // token可选（如果JWT拦截已校验，这里可简化）
    ) {
        try {
            // 1. 根据订单ID查询订单是否存在
            OrderInfo order = orderService.getById(orderId);
            if (order == null) {
                return R.fail("订单不存在！");
            }

            // 2. 修改订单状态
            order.setStatus(status);
            boolean updateSuccess = orderService.updateById(order);

            // 3. 返回结果
            if (updateSuccess) {
                return R.ok("订单状态修改成功！");
            } else {
                return R.fail("订单状态修改失败，请重试！");
            }
        } catch (Exception e) {
            // 捕获异常，返回友好提示（期末作业必备的异常处理）
            e.printStackTrace();
            return R.fail("系统异常，修改失败：" + e.getMessage());
        }
    }
}