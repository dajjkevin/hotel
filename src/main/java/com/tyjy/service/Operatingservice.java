package com.tyjy.service;


import com.tyjy.pojo.Operating;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface Operatingservice {
    HashMap<String,Object> select(Operating operating, HttpServletRequest request);

    HashMap<String,Object> selectback(Operating operating, HttpServletRequest request);

    HashMap<String,Object> selectDepartment(Operating operating, HttpServletRequest request);

    String add(Operating operating);

    String del(Operating operating, HttpServletRequest request);

    String delSession(Operating operating, HttpServletRequest request);

    String depart(Operating operating, HttpServletRequest request);
}
