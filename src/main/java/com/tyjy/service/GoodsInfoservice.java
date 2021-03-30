package com.tyjy.service;


import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.Operating;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface GoodsInfoservice {

    HashMap<String,Object> select(GoodsInfo goodsInfo, HttpServletRequest request);

    String departSession(GoodsInfo goodsInfo, HttpServletRequest request);

}
