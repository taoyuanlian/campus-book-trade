package com.whxy.campusbooktrade2.controller;

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.service.OrderService;
import com.whxy.campusbooktrade2.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 原有接口...
    @PostMapping("/create")
    public R<String> createOrder(@RequestBody OrderInfo orderInfo) {
        return orderService.createOrder(orderInfo);
    }

    @GetMapping("/my/{userId}")
    public R<List<OrderVo>> getMyOrders(@PathVariable Long userId) {
        return orderService.getMyOrders(userId);
    }

    @PutMapping("/updateStatus/{orderId}")
    public R<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Integer status,
            @RequestHeader(required = false) String token
    ) {
        try {
            OrderInfo order = orderService.getById(orderId);
            if (order == null) {
                return R.fail("订单不存在！");
            }
            order.setStatus(status);
            boolean updateSuccess = orderService.updateById(order);
            if (updateSuccess) {
                return R.ok("订单状态修改成功！");
            } else {
                return R.fail("订单状态修改失败，请重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("系统异常，修改失败：" + e.getMessage());
        }
    }

    // 新增：删除订单接口
    @DeleteMapping("/delete/{orderId}")
    public R<String> deleteOrder(
            @PathVariable Long orderId,
            @RequestHeader(required = false) String token
    ) {
        try {
            // 1. 校验订单是否存在
            OrderInfo order = orderService.getById(orderId);
            if (order == null) {
                // 调用R.fail返回「订单不存在」提示
                return R.fail("订单不存在，删除失败！");
            }

            // 2. 新增：校验订单状态（已支付订单不能删除）
            if (order.getStatus() == 2) {
                // 调用R.fail返回「已支付不可删」提示
                return R.fail("已支付的订单不允许删除！");
            }

            // 3. 执行删除
            boolean deleteSuccess = orderService.removeById(orderId);
            if (deleteSuccess) {
                // 可选：用新增的ok(String msg)返回自定义成功提示
                return R.ok("订单删除成功！");
            } else {
                return R.fail("订单删除失败，请重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("系统异常，删除失败：" + e.getMessage());
        }
    }
}