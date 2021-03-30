package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.DishInfoDao;
import com.tyjy.dao.OrderInfoDao;
import com.tyjy.pojo.DishInfo;
import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.OrderInfo;
import com.tyjy.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DishInfoserviceImpl implements DishInfoservice{

    @Autowired(required = false)
    DishInfoDao dishInfoDao;

    @Autowired(required = false)
    OrderInfoDao orderInfoDao;

    @Override
    public HashMap<String, Object> select(DishInfo dishInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(dishInfo.getPage(),dishInfo.getRow());
        List<DishInfo> list = new ArrayList<>();
        if(dishInfo.getDepartment()==null || dishInfo.getDepartment().equals("")){
            list = dishInfoDao.select(dishInfo);
        }
        else {
            list = dishInfoDao.selectBydepart(dishInfo);
        }
        PageInfo<DishInfo> page = new PageInfo<DishInfo>(list);
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
    public HashMap<String, Object> selectType(DishInfo dishInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(dishInfo.getPage(),dishInfo.getRow());
        List<DishInfo> list = new ArrayList<>();
        if(dishInfo.getType()==null || dishInfo.getType().equals("")){
            list = dishInfoDao.select(dishInfo);
        }
        else {
            list = dishInfoDao.selectBytype(dishInfo);
        }
        PageInfo<DishInfo> page = new PageInfo<DishInfo>(list);
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
    public String add(DishInfo dishInfo) {
        int count=dishInfoDao.countBydishName(dishInfo);
        if(count>0){
            return "该菜品已保存";
        }
        else {
            int num=dishInfoDao.add(dishInfo);
            if(num>0){
                return "添加成功";
            }
        }
        return "添加失败";
    }

    @Override
    public List<DishInfo> selectType() {
        return dishInfoDao.selectType();
    }

    @Override
    public List<DishInfo> selectTable() {
        return dishInfoDao.selectTable();
    }

    @Override
    public String buy(DishInfo dishInfo,HttpServletRequest request) {
        dishInfo=dishInfoDao.selectByid(dishInfo);
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setDishId(dishInfo.getDishId());
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        orderInfo.setUserId(userInfo.getUserId());
        int count=orderInfoDao.selectCountNumber(orderInfo);
        int num=0;
        if(count>0){
            orderInfo=orderInfoDao.selectBydish(orderInfo);
            orderInfo.setNumber(orderInfo.getNumber()+1);
            num=orderInfoDao.updatenumber(orderInfo);
        }
        else {
            orderInfo.setNumber(1);
            num=orderInfoDao.add(orderInfo);
        }
        if(num>0){
            return "下单成功";
        }
        return "下单失败";
    }
}
