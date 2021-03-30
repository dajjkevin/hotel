package com.tyjy.controller;

import com.tyjy.pojo.UserInfo;
import com.tyjy.service.UserInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserInfoController {

    @Autowired
    UserInfoservice userInfoservice;

    @RequestMapping("/list")
    public String list(UserInfo userInfo , ModelMap modelMap,HttpServletRequest request)
    {
        HashMap<String,Object> map=userInfoservice.select(userInfo,request);
        modelMap.put("info",map);
        modelMap.put("vv",userInfo.getConValue());
        return "user/user-list";
    }

    @RequestMapping("/integral")
    public String integral(UserInfo userInfo , ModelMap modelMap, HttpServletRequest request)
    {
        HashMap<String,Object> map=userInfoservice.select(userInfo,request);
        modelMap.put("info",map);
        modelMap.put("vv",userInfo.getConValue());
        return "user/user-integral";
    }


    @RequestMapping("/integralsort")
    @ResponseBody
    public HashMap<String,Object> integralsort(UserInfo userInfo, HttpServletRequest request){
        HttpSession session=request.getSession();
        if(userInfo.getSort()>=1)
            session.setAttribute("sort",userInfo.getSort());
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("info","加载成功");
        return map;
    }
    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String,Object> del(UserInfo userInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String info=userInfoservice.del(userInfo);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public HashMap<String,Object> edit (UserInfo userInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String info=userInfoservice.edit(userInfo,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/editPage")
    public String changePage(){
        return "user/user-edit";
    }

    @RequestMapping("/editinfo")
    @ResponseBody
    public HashMap<String,Object> editinfo(UserInfo userInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String info=userInfoservice.editinfo(userInfo,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/table")
    @ResponseBody
    public List<UserInfo> table(){
        return userInfoservice.selecttable();
    }

    @RequestMapping("/editcheckPage")
    public String editcheckPage(){
        return "user/user-editcheck";
    }

    @RequestMapping("/editownPage")
    public String editownPage(){
        return "user/user-editown";
    }
}
