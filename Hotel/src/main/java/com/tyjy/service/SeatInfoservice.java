package com.tyjy.service;



import com.tyjy.pojo.OrderSeatInfo;
import com.tyjy.pojo.SeatInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface SeatInfoservice {
    HashMap<String,Object> select(SeatInfo seatInfo, HttpServletRequest request);

    SeatInfo selectById(SeatInfo seatInfo);

    String order(OrderSeatInfo orderSeatInfo,HttpServletRequest request);

    HashMap<String,Object> selectByUser(OrderSeatInfo orderSeatInfo, HttpServletRequest request);

    String delorder(OrderSeatInfo orderSeatInfo, HttpServletRequest request);

    HashMap<String,Object> selectByseatId(OrderSeatInfo orderSeatInfo, HttpServletRequest request);

    HashMap<String,Object>  selectByday(OrderSeatInfo orderSeatInfo, HttpServletRequest request);

    String come(SeatInfo seatInfo);

    String leave(SeatInfo seatInfo);
}
