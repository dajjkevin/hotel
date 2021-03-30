package com.tyjy.controller;


import com.tyjy.pojo.SupplierInfo;
import com.tyjy.service.SupplierInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/supplier")
public class SupplierInfoController {

    @Autowired
    SupplierInfoservice supplierInfoservice;

    @RequestMapping("/list")
    public String list(SupplierInfo supplierInfo, ModelMap modelMap, HttpServletRequest request){
        HashMap<String,Object> map=supplierInfoservice.select(supplierInfo,request);
        modelMap.put("info",map);
        return "supplier/supplier-list";
    }

    @RequestMapping("/addPage")
    public String addPage(){
        return "supplier/supplier-add";
    }


    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String,Object> add(SupplierInfo supplierInfo, HttpServletRequest request){
        HashMap<String,Object> map=new HashMap<String, Object>();
        String info=supplierInfoservice.add(supplierInfo);
        map.put("info",info);
        return map;
    }
}
