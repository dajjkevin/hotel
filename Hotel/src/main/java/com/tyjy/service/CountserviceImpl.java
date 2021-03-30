package com.tyjy.service;

import com.tyjy.dao.*;
import com.tyjy.pojo.Count;
import com.tyjy.pojo.DishInfo;
import com.tyjy.pojo.OrderInfo;
import com.tyjy.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountserviceImpl implements Countservice {

    @Autowired(required = false)
    AdminInfoDao adminInfoDao;

    @Autowired(required = false)
    UserInfoDao userInfoDao;

    @Autowired(required = false)
    OperatingDao operatingDao;

    @Autowired(required = false)
    OrderInfoDao orderInfoDao;

    @Autowired(required = false)
    DishInfoDao dishInfoDao;

    @Autowired(required = false)
    SeatInfoDao seatInfoDao;

    @Override
    public void allCount(HttpServletRequest request) {
        adminCount(request);
        userCount(request);
        getgoodsCount(request);
        backgoodsCount(request);
        orderCount(request);
        dishCount(request);
        seatCount(request);
        orderNoCount(request);
    }

    @Override
    public void adminCount(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(adminInfoDao.selectCount()+"");
        count.setNow(adminInfoDao.selectCountNow()+"");
        count.setPre(adminInfoDao.selectCountPre()+"");
        count.setWeek(adminInfoDao.selectCountWeek()+"");
        count.setMouth(adminInfoDao.selectCountMouth()+"");
        session.setAttribute("adminTime",count);
    }

    @Override
    public void userCount(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(userInfoDao.selectCount()+"");
        count.setNow(userInfoDao.selectCountNow()+"");
        count.setPre(userInfoDao.selectCountPre()+"");
        count.setWeek(userInfoDao.selectCountWeek()+"");
        count.setMouth(userInfoDao.selectCountMouth()+"");
        session.setAttribute("userTime",count);
    }

    @Override
    public void dishCount(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(dishInfoDao.selectCount()+"");
        session.setAttribute("dishTime",count);
    }

    @Override
    public void seatCount(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(seatInfoDao.selectCount()+"");
        session.setAttribute("seatTime",count);
    }

    @Override
    public void orderNoCount(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(orderInfoDao.selectCountPay()+"");
        session.setAttribute("orderTime",count);
    }

    public void getgoodsCount(HttpServletRequest request){
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(operatingDao.selectCount()+"");
        count.setNow(operatingDao.selectCountNow()+"");
        count.setPre(operatingDao.selectCountPre()+"");
        count.setWeek(operatingDao.selectCountWeek()+"");
        count.setMouth(operatingDao.selectCountMouth()+"");
        session.setAttribute("getgoodsTime",count);
    }

    public void backgoodsCount(HttpServletRequest request){
        HttpSession session=request.getSession();
        Count count=new Count();
        count.setAll(operatingDao.selectbackCount()+"");
        count.setNow(operatingDao.selectbackCountNow()+"");
        count.setPre(operatingDao.selectbackCountPre()+"");
        count.setWeek(operatingDao.selectbackCountWeek()+"");
        count.setMouth(operatingDao.selectbackCountMouth()+"");
        session.setAttribute("backgoodsTime",count);
    }

    public void orderCount(HttpServletRequest request){
        HttpSession session =request.getSession();
        Count count=new Count();
        List<OrderInfo> list = new ArrayList<>();
        list=orderInfoDao.selectPayedAdmin();
        count.setAll(orderPrice(list));
        list.clear();

        list=orderInfoDao.selectPayedNow();
        count.setNow(orderPrice(list));
        list.clear();

        list=orderInfoDao.selectPayedPre();
        count.setPre(orderPrice(list));
        list.clear();

        list=orderInfoDao.selectPayedweek();
        count.setWeek(orderPrice(list));
        list.clear();

        list=orderInfoDao.selectPayedMouth();
        count.setMouth(orderPrice(list));
        session.setAttribute("priceTime",count);
    }

    public String orderPrice(List<OrderInfo> list){
        double price=0;
        double num=0;
        OrderInfo orderInfo=new OrderInfo();
        DishInfo dishInfo=new DishInfo();
        for(int i=0;i<list.size();i++){
            orderInfo=list.get(i);
            dishInfo.setDishId(orderInfo.getDishId());
            dishInfo=dishInfoDao.selectByid(dishInfo);
            num=dishInfo.getDishPrice()*orderInfo.getNumber();
            price +=num;
        }
        return String.format("%.2f",price);
    }
}
