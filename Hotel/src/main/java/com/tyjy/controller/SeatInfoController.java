package com.tyjy.controller;

import com.tyjy.pojo.OrderSeatInfo;
import com.tyjy.pojo.SeatInfo;
import com.tyjy.service.SeatInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/seat")
public class SeatInfoController {

    @Autowired
    SeatInfoservice seatInfoservice;

    @RequestMapping("/list")
    public String list(SeatInfo seatInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=seatInfoservice.select(seatInfo,request);
        modelMap.put("info",map);
        return "seat/seat-list";
    }

    @RequestMapping("/listadmin")
    public String listadmin(SeatInfo seatInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=seatInfoservice.select(seatInfo,request);
        modelMap.put("info",map);
        return "seat/seat-listadmin";
    }

    @RequestMapping("/orderPage")
    public String orderseatPage(SeatInfo seatInfo,ModelMap modelMap){
        SeatInfo s=seatInfoservice.selectById(seatInfo);
        modelMap.put("seatinfo",s);
        return "seat/seat-order";
    }


    @ResponseBody
    @RequestMapping("/order")
    public HashMap<String,Object> order(OrderSeatInfo orderSeatInfo,HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info= seatInfoservice.order(orderSeatInfo,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/come")
    @ResponseBody
    public HashMap<String,Object> come(SeatInfo seatInfo){
        String info=seatInfoservice.come(seatInfo);
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("info",info);
        return map;
    }

    @RequestMapping("/leave")
    @ResponseBody
    public HashMap<String,Object> leave(SeatInfo seatInfo){
        String info=seatInfoservice.leave(seatInfo);
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("info",info);
        return map;
    }
}
