package com.whxy.campusbooktrade2.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类，和User结构一致，用lombok@Data简化代码
 */
@Data
public class OrderInfo {
    // 订单ID
    private Long id;
    // 订单编号（唯一，用于区分订单）
    private String orderNo;
    // 下单用户ID（关联User表的id）
    private Long userId;
    // 购买的书籍ID（关联Book表的id）
    private Long bookId;
    // 订单金额
    private BigDecimal price;
    // 订单状态：1-待支付 2-已支付 3-已取消（期末作业简化）
    private Integer status;
    // 创建时间
    private Date createTime;
}