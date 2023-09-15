package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    void insertOredr(Orders order);

    @Select("select * from orders  where number=#{outTradeNo}")
    Orders getByNumber(String outTradeNo);

    void update(Orders orders);
}
