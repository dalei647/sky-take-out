package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {


    void insertBatch(List<OrderDetail> orderdetails);

    /**
     * 条件查询
     * @param orderDetail
     */
    List<OrderDetail> select(OrderDetail orderDetail);

    /**
     * 根据订单id查询
     * @param orderId
     * @return
     */
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
