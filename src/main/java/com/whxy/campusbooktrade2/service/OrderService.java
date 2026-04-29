package com.whxy.campusbooktrade2.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.vo.OrderVo;

import java.util.List;
/**
 * 订单Service，调整返回值为OrderVo
 */
public interface OrderService extends IService<OrderInfo> {
    // 创建订单（下单）
    R<String> createOrder(OrderInfo orderInfo);
    // 查询我的订单（根据用户ID）- 返回OrderVo列表（包含书名）
    R<List<OrderVo>> getMyOrders(Long userId);
}