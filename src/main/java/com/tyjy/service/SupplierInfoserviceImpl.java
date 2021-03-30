package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.SupplierInfoDao;
import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SupplierInfoserviceImpl implements SupplierInfoservice {

    @Autowired(required = false)
    SupplierInfoDao supplierInfoDao;

    @Override
    public HashMap<String, Object> select(SupplierInfo supplierInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(supplierInfo.getPage(),supplierInfo.getRow());
        List<SupplierInfo> list = new ArrayList<>();
        list = supplierInfoDao.select();
        PageInfo<SupplierInfo> page = new PageInfo<SupplierInfo>(list);
        map.put("list",page.getList());
        map.put("total",page.getTotal());
        map.put("totalPage",page.getPages());
        if(page.getPrePage()==0){
            map.put("prePage",1);
        }
        else {
            map.put("prePage", page.getPrePage());
        }
        if(page.getNextPage()==0){
            map.put("nextPage",page.getPages());
        }
        else {
            map.put("nextPage",page.getNextPage());
        }
        map.put("cur",page.getPageNum());
        return map;
    }

    @Override
    public String add(SupplierInfo supplierInfo) {
        int count =supplierInfoDao.selectcount(supplierInfo);
        if(count>0){
            return "该供货商已保存";
        }
        else {
            int num=supplierInfoDao.add(supplierInfo);
            if(num>0){
                return "添加成功";
            }
            else {
                return "添加失败";
            }
        }
    }
}
