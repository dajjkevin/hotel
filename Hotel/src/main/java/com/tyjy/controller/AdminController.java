package com.tyjy.controller;

import com.tyjy.pojo.AdminInfo;
import com.tyjy.service.AdminInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminInfoservice adminInfoservice;

    @RequestMapping("/welcome")
    String welcome(){
        return "admin/welcome";
    }
    @RequestMapping("/xadmin")
    String index (){
        return "admin/xadmin";
    }

    @RequestMapping("/list")
    String list (AdminInfo adminInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=adminInfoservice.select(adminInfo,request);
        modelMap.put("info",map);
        return "admin/admin-list";
    }
    @RequestMapping("/department")
    @ResponseBody
    public List<AdminInfo> department(){
        return adminInfoservice.selectDepart();
    }

    @RequestMapping("/addPage")
    String addPage (){
        return "admin/admin-add";
    }

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String,Object> add(AdminInfo adminInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info =adminInfoservice.add(adminInfo);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/editPwdPage")
    String editPwdPage (){
        return "admin/admin-editPwd";
    }

    @RequestMapping("/editPwd")
    @ResponseBody
    public HashMap<String,Object> editPwd(AdminInfo adminInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info =adminInfoservice.editPwd(adminInfo,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/Pwd")
    @ResponseBody
    public HashMap<String,Object> Pwd(AdminInfo adminInfo){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info =adminInfoservice.Pwd(adminInfo);
        map.put("info",info);
        return map;
    }
}
