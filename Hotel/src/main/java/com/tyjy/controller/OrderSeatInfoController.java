package com.tyjy.controller;

import com.tyjy.pojo.OrderSeatInfo;
import com.tyjy.pojo.SeatInfo;
import com.tyjy.service.SeatInfoservice;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/orderseat")
public class OrderSeatInfoController {

    @Autowired
    SeatInfoservice seatInfoservice;

    @RequestMapping("/user")
    public String orderuser(OrderSeatInfo orderSeatInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=seatInfoservice.selectByUser(orderSeatInfo,request);
        modelMap.put("info",map);
        return "seat/seat-orderuser";
    }

    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String,Object> del(OrderSeatInfo orderSeatInfo,HttpServletRequest request){
        String info=seatInfoservice.delorder(orderSeatInfo,request);
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("info",info);
        return map;
    }

    @RequestMapping("/admin")
    public String admin(OrderSeatInfo orderSeatInfo,ModelMap modelMap,HttpServletRequest request){
        HashMap<String,Object> map=seatInfoservice.selectByseatId(orderSeatInfo,request);
        modelMap.put("info",map);
        return "seat/seat-listadminorder";
    }

    @RequestMapping("/day")
    public String day(OrderSeatInfo orderSeatInfo,ModelMap modelMap,HttpServletRequest request){
        HashMap<String,Object> map=seatInfoservice.selectByday(orderSeatInfo,request);
        modelMap.put("info",map);
        return "seat/seat-listorderday";
    }


}
