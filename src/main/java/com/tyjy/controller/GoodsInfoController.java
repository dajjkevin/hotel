package com.tyjy.controller;

import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.Operating;
import com.tyjy.service.GoodsInfoservice;
import com.tyjy.service.Operatingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/goods")
public class GoodsInfoController {

    @Autowired
    GoodsInfoservice goodsInfoservice;

    @Autowired
    Operatingservice operatingservice;

    @RequestMapping("/list")
    public String list(GoodsInfo goodsInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=goodsInfoservice.select(goodsInfo,request);
        modelMap.put("info",map);
        return "goods/goods-list";
    }
    @RequestMapping("/departSession")
    @ResponseBody
    public HashMap<String, Object> departSession(GoodsInfo goodsInfo, HttpServletRequest request){
        String info= goodsInfoservice.departSession(goodsInfo,request);
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("info",info);
        return map;
    }
}
