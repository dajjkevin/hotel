package com.tyjy.controller;

import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.UserInfo;
import com.tyjy.service.AdminInfoservice;
import com.tyjy.service.Countservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

@Controller

public class AdminLoginController {
    @Autowired
    AdminInfoservice adminInfoservice;

    @Autowired
    Countservice countservice;

    @RequestMapping("/adminloginPage")
    String adminloginPage (HttpServletRequest request){
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return "admin/adminLogin";
    }

    @RequestMapping("/adminlogin")
    @ResponseBody
    public HashMap<String,Object> adminlogin(AdminInfo adminInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        countservice.allCount(request);
        String info =adminInfoservice.Login(adminInfo,request);
        map.put("info",info);
        return map;
    }
}
