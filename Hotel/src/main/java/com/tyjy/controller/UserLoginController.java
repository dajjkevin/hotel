package com.tyjy.controller;

import com.tyjy.pojo.UserInfo;
import com.tyjy.service.UserLoginservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserLoginController {
    @Autowired(required = false)
    UserLoginservice userLoginservice;

    @RequestMapping("/loginPage")
    public String LoginPage(HttpServletRequest request){
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return "user/userLogin";
    }

    @RequestMapping("/login")
    @ResponseBody
    public HashMap<String,Object> login(UserInfo userInfo, HttpServletRequest httpServletRequest){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String info= userLoginservice.login(userInfo,httpServletRequest);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/signin")
    @ResponseBody
    public HashMap<String,Object> signin(UserInfo userInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String info= userLoginservice.signin(userInfo,request);
        map.put("info",info);
        return map;
    }
    //发送验证码
    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String,Object> sendmail(String email, HttpServletRequest request){
        return userLoginservice.sendCode(email,request);
    }
    //邮件登录
    @RequestMapping("/loginemail")
    @ResponseBody
    public HashMap<String,Object> loginemail(String code,HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        //验证验证码是否一致
        HttpSession session=request.getSession();
        String valcode=session.getAttribute("valCode").toString();
        code=code.toUpperCase();
        if(code.equals(valcode)){
            String info=userLoginservice.emailLogin(request);
            map.put("info",info);
        }
        else {
            map.put("info","验证码错误");
        }
        return map;
    }

    @RequestMapping("/checkPage")
    public String check(){
        return "user/user-check";
    }

    @RequestMapping("/sendcheckemail")
    @ResponseBody
    public HashMap<String,Object> sendcheckemail(UserInfo userInfo, HttpServletRequest request){
        return userLoginservice.sendcheckCode(userInfo,request);
    }
    @RequestMapping("/checkemail")
    @ResponseBody
    public HashMap<String,Object> checkemail(String code,HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        //验证验证码是否一致
        HttpSession session=request.getSession();
        String valcode=session.getAttribute("checkvalCode").toString();
        code=code.toUpperCase();
        if(code.equals(valcode)){
            map.put("info","验证成功");
        }
        else {
            map.put("info","验证码错误");
        }
        return map;
    }

    @RequestMapping("/changePwdPage")
    public String changePwdPage(){
        return "user/user-changePwd";
    }


    @RequestMapping("/changePwd")
    @ResponseBody
    public HashMap<String,Object> changePwd(UserInfo userInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info = userLoginservice.changePwd(userInfo,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/emailPage")
    public String emailPage(){
        return "user/user-email";
    }

    @RequestMapping("/sendemail")
    @ResponseBody
    public HashMap<String,Object> sendemail(UserInfo userInfo, HttpServletRequest request){
        return userLoginservice.emailCode(userInfo,request);
    }
    @RequestMapping("/email")
    @ResponseBody
    public HashMap<String,Object> email(String code,HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        //验证验证码是否一致
        HttpSession session=request.getSession();
        String valcode=session.getAttribute("emailvalCode").toString();
        code=code.toUpperCase();
        if(code.equals(valcode)){
            if(userLoginservice.email(request)==1)
            map.put("info","绑定成功");
            else map.put("info","绑定失败");
        }
        else {
            map.put("info","验证码错误");
        }
        return map;
    }
}
