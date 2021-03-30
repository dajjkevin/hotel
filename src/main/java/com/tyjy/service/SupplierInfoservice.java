package com.tyjy.service;

import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.SupplierInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface SupplierInfoservice {
    HashMap<String,Object> select(SupplierInfo supplierInfo, HttpServletRequest request);

    String add(SupplierInfo supplierInfo);
}
