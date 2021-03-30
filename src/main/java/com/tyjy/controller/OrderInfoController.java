package com.tyjy.controller;


import com.tyjy.pojo.OrderInfo;
import com.tyjy.service.Orderservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderInfoController {
    @Autowired
    Orderservice orderservice;

    @RequestMapping("/list")
    public String list(OrderInfo orderInfo,ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=orderservice.selectPay(orderInfo,request);
        modelMap.put("info",map);
        return "order/order-list";
    }
    @RequestMapping("/listpayed")
    public String listpayed(OrderInfo orderInfo,ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=orderservice.selectPayed(orderInfo,request);
        modelMap.put("info",map);
        return "order/order-listpayed";
    }

    @RequestMapping("/listadmin")
    public String listadmin(OrderInfo orderInfo,ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=orderservice.selectAdmin(orderInfo,request);
        modelMap.put("info",map);
        return "order/order-listadmin";
    }

    @ResponseBody
    @RequestMapping("/payNo")
    public List<OrderInfo> payNo(){
        return orderservice.selectPayNo();
    }
}
