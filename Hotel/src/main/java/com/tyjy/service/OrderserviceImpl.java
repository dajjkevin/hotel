package com.tyjy.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.DishInfoDao;
import com.tyjy.dao.OrderInfoDao;
import com.tyjy.dao.PayMentDao;
import com.tyjy.dao.UserInfoDao;
import com.tyjy.pojo.*;
import com.tyjy.util.OrderUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderserviceImpl implements Orderservice{
    @Autowired(required = false)
    OrderInfoDao orderInfoDao;

    @Autowired(required = false)
    DishInfoDao dishInfoDao;

    @Autowired(required = false)
    OrderUtil orderUtil;

    @Autowired(required = false)
    UserInfoDao userInfoDao;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Autowired(required = false)
    PayMentDao payMentDao;

    private UserInfo userInfo;



    @Override
    public HashMap<String, Object> selectPay(OrderInfo orderInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(orderInfo.getPage(),orderInfo.getRow());
        List<OrderInfo> list = new ArrayList<>();
        HttpSession session=request.getSession();
        userInfo=(UserInfo)session.getAttribute("userinfo");
        session.setAttribute("userinfo",userInfo);
        orderInfo.setUserId(userInfo.getUserId());
        list = orderInfoDao.selectPay(orderInfo);
        for(int i=0;i<list.size();i++){
            OrderInfo o=list.get(i);
            DishInfo dishInfo=new DishInfo();
            dishInfo.setDishId(o.getDishId());
            dishInfo=dishInfoDao.selectByid(dishInfo);
            o.setDishPrice(dishInfo.getDishPrice());
            o.setDishName(dishInfo.getDishName());
            list.set(i,o);
        }
        PageInfo<OrderInfo> page = new PageInfo<OrderInfo>(list);
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
    public HashMap<String, Object> selectPayed(OrderInfo orderInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(orderInfo.getPage(),orderInfo.getRow());
        List<OrderInfo> list = new ArrayList<>();
        HttpSession session=request.getSession();
        userInfo=(UserInfo)session.getAttribute("userinfo");
        session.setAttribute("userinfo",userInfo);
        orderInfo.setUserId(userInfo.getUserId());
        if(orderInfo.getPayNo()==null || orderInfo.getPayNo().equals("")){
            list = orderInfoDao.selectPayed(orderInfo);
        }
        else {
            list = orderInfoDao.selectByPayNo(orderInfo);
        }
        for(int i=0;i<list.size();i++){
            OrderInfo o=list.get(i);
            DishInfo dishInfo=new DishInfo();
            dishInfo.setDishId(o.getDishId());
            dishInfo=dishInfoDao.selectByid(dishInfo);
            o.setDishPrice(dishInfo.getDishPrice());
            o.setDishName(dishInfo.getDishName());
            list.set(i,o);
        }
        PageInfo<OrderInfo> page = new PageInfo<OrderInfo>(list);
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
    public HashMap<String, Object> selectAdmin(OrderInfo orderInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(orderInfo.getPage(),orderInfo.getRow());
        List<OrderInfo> list = new ArrayList<>();
        List<OrderInfo> listAdmin = new ArrayList<>(10);
        HttpSession session=request.getSession();
        AdminInfo adminInfo=(AdminInfo) session.getAttribute("admin");
        list = orderInfoDao.selectPayAdmin();
        for(int i=0,y=0;i<list.size();i++){
            OrderInfo o=list.get(i);
            DishInfo dishInfo=new DishInfo();
            dishInfo.setDishId(o.getDishId());
            dishInfo=dishInfoDao.selectByid(dishInfo);
            o.setDishPrice(dishInfo.getDishPrice());
            o.setDishName(dishInfo.getDishName());
            if(adminInfo.getDepartment().equals(dishInfo.getDepartment())){
                listAdmin.add(o);
            }
        }
        PageInfo<OrderInfo> page = new PageInfo<OrderInfo>(listAdmin);
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
    public String pay(PayMent payMent, HttpServletRequest request) {
        HttpSession session=request.getSession();
        userInfo=(UserInfo)session.getAttribute("userinfo");
        session.setAttribute("userinfo",userInfo);
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setUserId(userInfo.getUserId());
        List<OrderInfo> list = new ArrayList<>();
        list =orderInfoDao.selectByUserId(orderInfo);
        DishInfo dishInfo =new DishInfo();
        payMent=new PayMent();
        double total = 0;
        String subject = "";
        for(int i=0;i<list.size();i++){
            OrderInfo o =list.get(i);
            dishInfo.setDishId(o.getDishId());
            dishInfo = dishInfoDao.selectByid(dishInfo);
            total+=dishInfo.getDishPrice() * (double) o.getNumber();
            subject +=dishInfo.getDishName();
            subject +=" ";
        }
        payMent.setSubject(subject);
        if(userInfo.getIntegral()>5000){
            total*=0.8;
        }
        else if(userInfo.getIntegral()>2000){
            total*=0.9;
        }
        else if(userInfo.getIntegral()>100){
            total*=0.95;
        }
        else {
            total*=1;
        }
        payMent.setPayTotal(String.format("%.2f",total));
        try {
            AlipayTradePagePayResponse response = Factory.Payment
                    .Page()
                    .pay(payMent.getSubject(),orderUtil.getOrderNo(),payMent.getPayTotal(),returnUrl);

            return response.body;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payed(Map<String, String> params, HttpServletRequest request) {
        System.out.println("交易名称: " + params.get("subject"));
        System.out.println("交易状态: " + params.get("trade_status"));
        System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
        System.out.println("商户订单号: " + params.get("out_trade_no"));
        System.out.println("交易金额: " + params.get("total_amount"));
        System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
        System.out.println("买家付款时间: " + params.get("gmt_payment"));
        System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
        PayMent payMent= new PayMent();
        payMent.setPayNo(params.get("out_trade_no"));
        payMent.setSubject(params.get("subject"));
        payMent.setTime(params.get("gmt_payment"));
        payMent.setPayTotal(params.get("total_amount"));
        payMent.setBuyer_pay_amount(params.get("buyer_pay_amount"));
        payMent.setBuyerId(params.get("buyer_id"));
        payMent.setTradeNo(params.get("trade_no"));
        payMentDao.add(payMent);
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setUserId(userInfo.getUserId());
        orderInfo.setPayNo(params.get("out_trade_no"));
        orderInfoDao.pay(orderInfo);

        double inf=userInfo.getIntegral();
        inf+=Double.valueOf(payMent.getPayTotal())*0.1;
        userInfo.setIntegral(inf);
        userInfoDao.updateinf(userInfo);
        return null;
    }

    @Override
    public List<OrderInfo> selectPayNo() {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setUserId(userInfo.getUserId());
        return orderInfoDao.selectPayNo(orderInfo);
    }
}
