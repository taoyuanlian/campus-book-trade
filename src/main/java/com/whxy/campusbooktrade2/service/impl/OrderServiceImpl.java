package com.whxy.campusbooktrade2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.mapper.OrderMapper;
import com.whxy.campusbooktrade2.service.OrderService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单Service实现类，和UserServiceImpl结构一致
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderInfo> implements OrderService {

    /**
     * 创建订单（核心逻辑：生成唯一订单号 + 保存订单）
     */
    @Override
    public R<String> createOrder(OrderInfo orderInfo) {
        try {
            // 1. 生成唯一订单号（时间戳+随机数，期末作业简化版）
            String orderNo = System.currentTimeMillis() + "" + new Random().nextInt(1000);
            orderInfo.setOrderNo(orderNo);

            // 2. 设置默认值
            orderInfo.setStatus(1); // 1-待支付
            orderInfo.setCreateTime(new Date());

            // 3. 保存订单到数据库
            boolean save = save(orderInfo);
            if (save) {
                return R.ok("下单成功！订单号：" + orderNo);
            } else {
                return R.fail("下单失败，请重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("系统异常，下单失败：" + e.getMessage());
        }
    }

    /**
     * 查询我的订单（根据用户ID筛选）
     */
    @Override
    public R<List<OrderInfo>> getMyOrders(Long userId) {
        // 构造查询条件：只查当前用户的订单
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        wrapper.orderByDesc(OrderInfo::getCreateTime); // 按创建时间倒序

        // 查询订单列表
        List<OrderInfo> orderList = list(wrapper);
        return R.ok(orderList);
    }
}