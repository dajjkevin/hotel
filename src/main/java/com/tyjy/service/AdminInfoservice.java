package com.tyjy.service;

import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface AdminInfoservice {

    String Login(AdminInfo adminInfo, HttpServletRequest request);

    HashMap<String,Object> select(AdminInfo adminInfo, HttpServletRequest request);

    List<AdminInfo> selectDepart();

    String add(AdminInfo adminInfo);

    String editPwd(AdminInfo adminInfo, HttpServletRequest request);

    String Pwd(AdminInfo adminInfo);

    String del(AdminInfo adminInfo, HttpServletRequest request);
}
