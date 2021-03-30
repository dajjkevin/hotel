package com.tyjy.controller;

import com.tyjy.pojo.Operating;
import com.tyjy.service.Operatingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/operating")
public class OperatingController {

    @Autowired
    Operatingservice operatingservice;

    @RequestMapping("/list")
    public String list(Operating operating, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=operatingservice.select(operating,request);
        modelMap.put("info",map);
        return "operating/operating-list";
    }

    @RequestMapping("/listBack")
    public String listBack(Operating operating, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=operatingservice.selectback(operating,request);
        modelMap.put("info",map);
        return "operating/operating-listback";
    }

    @RequestMapping("/addPage")
    public String addPage(){
        return "operating/operating-add";
    }

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String,Object> add(Operating operating, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info=operatingservice.add(operating);
        map.put("info",info);
        return map;
    }


    @RequestMapping("/delSession")
    @ResponseBody
    public HashMap<String,Object>  delSession(Operating operating,HttpServletRequest request){
        String info= operatingservice.delSession(operating,request);
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("info",info);
        return map;
    }

    @RequestMapping("/delPage")
    public String delPage(){
        return "operating/operating-del";
    }

    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String,Object>  del(Operating operating,HttpServletRequest request){
        String info= operatingservice.del(operating,request);
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("info",info);
        return map;
    }

    @RequestMapping("/listdepartment")
    public String listdepartment(Operating operating, ModelMap modelMap, HttpServletRequest request)
    {
        HashMap<String,Object> map=operatingservice.selectDepartment(operating,request);
        modelMap.put("info",map);
        return "operating/operating-listdepartment";
    }

    @RequestMapping("/departPage")
    public String departPage(){
        return "goods/goods-department";
    }
    @RequestMapping("/depart")
    @ResponseBody
    public HashMap<String,Object>  depart(Operating operating, HttpServletRequest request){
        String info=operatingservice.depart(operating,request) ;
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("info",info);
        return map;
    }
}
