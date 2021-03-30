package com.tyjy.controller;

import com.alipay.easysdk.factory.Factory;
import com.tyjy.pojo.OrderInfo;
import com.tyjy.pojo.PayMent;
import com.tyjy.service.Orderservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    Orderservice orderservice;
    @ResponseBody
    @PostMapping("/Page")
    public String page(PayMent payMent,HttpServletRequest request) throws Exception {
        payMent = new PayMent();
        return orderservice.pay(payMent,request);
    }

    @ResponseBody
    @PostMapping("/notifyUrl")
    public String notify_url(HttpServletRequest request) throws Exception {

        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                orderservice.payed(params,request);
            }
        }
        return "success";
    }
}
