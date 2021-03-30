package com.tyjy.service;

import com.tyjy.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserLoginservice {

    //登录
    String login(UserInfo user, HttpServletRequest request);
    //注册账号
    String signin(UserInfo userInfo, HttpServletRequest request);
    //邮箱登录
    HashMap<String, Object> sendCode(String email, HttpServletRequest request);
    //邮箱验证
    HashMap<String, Object> sendcheckCode(UserInfo userInfo, HttpServletRequest request);
    //邮箱绑定email
    HashMap<String, Object> emailCode(UserInfo userInfo, HttpServletRequest request);

    int email(HttpServletRequest request);
    //更改密码
    String changePwd(UserInfo userInfo, HttpServletRequest request);

    String emailLogin(HttpServletRequest request);

}
