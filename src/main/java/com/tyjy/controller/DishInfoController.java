package com.tyjy.controller;

import com.tyjy.pojo.DishInfo;
import com.tyjy.pojo.UserInfo;
import com.tyjy.service.DishInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/dish")
public class DishInfoController {

    @Autowired
    DishInfoservice dishInfoservice;

    @RequestMapping("/list")
    public String list(DishInfo dishInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=dishInfoservice.select(dishInfo,request);
        modelMap.put("info",map);
        return "dish/dish-list";
    }
    @RequestMapping("/listuser")
    public String listuser(DishInfo dishInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=dishInfoservice.selectType(dishInfo,request);
        modelMap.put("info",map);
        return "dish/dish-listuser";
    }

    @RequestMapping("/type")
    @ResponseBody
    public List<DishInfo> type(){
        return dishInfoservice.selectType();
    }

    @RequestMapping("/addPage")
    public String addPage(){
        return "dish/dish-add";
    }

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, Object> add(DishInfo dishInfo){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info=dishInfoservice.add(dishInfo);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/buy")
    @ResponseBody
    public HashMap<String, Object> buy(DishInfo dishInfo,HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info=dishInfoservice.buy(dishInfo,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/li")
    @ResponseBody
    public List<DishInfo> table(){
        return dishInfoservice.selectTable();
    }
}
