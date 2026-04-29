package com.whxy.campusbooktrade2.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whxy.campusbooktrade2.entity.OrderInfo;
import com.whxy.campusbooktrade2.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 订单Mapper，新增联表查询方法
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
    /**
     * 联表查询用户订单（包含书名）
     * @param userId 用户ID
     * @return 包含书名的订单列表
     */
    @Select("SELECT o.*, b.name as bookName " +
            "FROM order_info o " +
            "LEFT JOIN book b ON o.book_id = b.id " +
            "WHERE o.user_id = #{userId} " +
            "ORDER BY o.create_time DESC")
    List<OrderVo> selectMyOrdersWithBookName(@Param("userId") Long userId);
}