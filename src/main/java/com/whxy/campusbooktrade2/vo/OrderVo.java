package com.whxy.campusbooktrade2.vo;

import com.whxy.campusbooktrade2.entity.OrderInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单VO：扩展OrderInfo，新增书名字段
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderVo {
    // 继承OrderInfo的核心字段（也可以直接复制，避免继承）
    private Long id;
    private String orderNo;
    private Long userId;
    private Long bookId;
    private BigDecimal price;
    private Integer status;
    private Date createTime;

    // 新增：书籍名称（从book表关联查询）
    private String bookName;
}