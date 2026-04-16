package com.whxy.campusbooktrade2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import java.util.List;

/**
 * 订单Service，和UserService结构一致，返回R类型封装结果
 */
public interface OrderService extends IService<OrderInfo> {
    // 创建订单（下单）
    R<String> createOrder(OrderInfo orderInfo);

    // 查询我的订单（根据用户ID）
    R<List<OrderInfo>> getMyOrders(Long userId);
}