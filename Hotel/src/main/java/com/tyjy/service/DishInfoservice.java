package com.tyjy.service;

import com.tyjy.pojo.DishInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface DishInfoservice {

    HashMap<String,Object> select(DishInfo dishInfo, HttpServletRequest request);

    HashMap<String,Object> selectType(DishInfo dishInfo, HttpServletRequest request);

    String add(DishInfo dishInfo);

    List<DishInfo> selectType();

    List<DishInfo> selectTable();

    String buy(DishInfo dishInfo, HttpServletRequest request);
}
