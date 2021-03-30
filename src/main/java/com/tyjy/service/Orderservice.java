package com.tyjy.service;


import com.tyjy.pojo.OrderInfo;
import com.tyjy.pojo.PayMent;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Orderservice {
    HashMap<String,Object> selectPay(OrderInfo orderInfo, HttpServletRequest request);

    HashMap<String,Object> selectPayed(OrderInfo orderInfo, HttpServletRequest request);

    HashMap<String,Object> selectAdmin(OrderInfo orderInfo, HttpServletRequest request);

    String pay(PayMent payMent, HttpServletRequest request);

    String payed(Map<String, String> map,HttpServletRequest request);

    List<OrderInfo> selectPayNo();
}
