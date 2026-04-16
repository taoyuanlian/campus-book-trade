package com.whxy.campusbooktrade2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper，和UserMapper结构完全一致
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
}