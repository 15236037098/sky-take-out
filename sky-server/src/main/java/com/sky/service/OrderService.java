package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import java.util.List;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult pageQueryOrder(int page, int pageSize, Integer status);

    void userCancelById(Long id) throws Exception;

    void repetition(Long id);

    OrderVO detailsOrder(Long id);

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    void cancel(OrdersCancelDTO ordersCancelDTO);

    OrderStatisticsVO statistics();

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    void delivery(Long id);

    void complete(Long id);


}
