package com.tyjy.service;

import com.tyjy.pojo.UserInfo;
import org.apache.catalina.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UserInfoservice {
    HashMap<String,Object> select(UserInfo userInfo, HttpServletRequest request);

    String del(UserInfo userInfo);

    String editinfo(UserInfo userInfo, HttpServletRequest request);

    String edit(UserInfo userInfo, HttpServletRequest request);

    List<UserInfo> selecttable();
}
