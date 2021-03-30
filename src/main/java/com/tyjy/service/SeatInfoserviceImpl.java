package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.OrderSeatInfoDao;
import com.tyjy.dao.SeatInfoDao;
import com.tyjy.dao.UserInfoDao;
import com.tyjy.pojo.OrderSeatInfo;
import com.tyjy.pojo.SeatInfo;
import com.tyjy.pojo.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Select;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class SeatInfoserviceImpl  implements SeatInfoservice{

    @Autowired(required = false)
    SeatInfoDao seatInfoDao;

    @Autowired(required = false)
    OrderSeatInfoDao orderSeatInfoDao;

    @Autowired(required = false)
    UserInfoDao userInfoDao;

    @Value("${spring.mail.username}")
    String sendEmail;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public HashMap<String, Object> select(SeatInfo seatInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(seatInfo.getPage(),seatInfo.getRow());
        List<SeatInfo> list = new ArrayList<>();
        list = seatInfoDao.select();
        for(int i=0;i<list.size();i++){
            SeatInfo s=list.get(i);
            if(s.getUseOrder()==0){
                s.setMessage("暂无人");
            }
            else if(s.getUseOrder()==1){
                s.setMessage("已被预定");
            }
            else {
                s.setMessage("已有人");
            }
            list.set(i,s);
        }
        PageInfo<SeatInfo> page = new PageInfo<SeatInfo>(list);
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
    public SeatInfo selectById(SeatInfo seatInfo) {
        return seatInfoDao.selectById(seatInfo);
    }

    @Override
    public String order(OrderSeatInfo orderSeatInfo,HttpServletRequest request) {
        int num1=orderSeatInfoDao.selectseatAftertime(orderSeatInfo);
        int num2=orderSeatInfoDao.selectseatBeforetime(orderSeatInfo);
        if(num1>0){
            return "此时间段已有人预定，请重新选择";
        }
        else if(num2>0){
            return "你预定的时间后面不久已经被预定，为了让你拥有充分的就餐时间，请重新选择";
        }
        else {
            HttpSession session = request.getSession();
            UserInfo userInfo= (UserInfo)session.getAttribute("userinfo");
            orderSeatInfo.setUserId(userInfo.getUserId());
            session.setAttribute("userinfo",userInfo);
            SeatInfo seatInfo=new SeatInfo();
            seatInfo.setSeatId(orderSeatInfo.getSeatId());
             seatInfo=seatInfoDao.selectById(seatInfo);
            int no=orderSeatInfoDao.order(orderSeatInfo);
            if(no>0){
                String Subject="预定成功信息";
                String Text="你已经成功预定"
                        +seatInfo.getSeatName()
                        +",预定时间为:"
                        +orderSeatInfo.getTime()
                        +"\n"
                        + "PS:我们最多为你保留1小时座位。"
                        ;
                sendEmail(Subject,Text,request);
                return "预定成功";
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> selectByUser(OrderSeatInfo orderSeatInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(orderSeatInfo.getPage(),orderSeatInfo.getRow());
        List<OrderSeatInfo> list = new ArrayList<OrderSeatInfo>();
        HttpSession session = request.getSession();
        UserInfo userInfo= (UserInfo)session.getAttribute("userinfo");
        orderSeatInfo.setUserId(userInfo.getUserId());
        session.setAttribute("userinfo",userInfo);
        list = orderSeatInfoDao.selectByUser(orderSeatInfo);
        for(int i=0;i<list.size();i++){
            OrderSeatInfo o=list.get(i);
            SeatInfo seatInfo=new SeatInfo();
            seatInfo.setSeatId(o.getSeatId());
            seatInfo=seatInfoDao.selectById(seatInfo);
            o.setSeatName(seatInfo.getSeatName());
            if(o.getCome()==0){
                o.setOrdermessage("暂未就餐");
            }
            else if(o.getCome()==-1){
                o.setOrdermessage("已经退掉预定");
            }
            else if(o.getCome()==-2){
                o.setOrdermessage("超时未就餐");
            }
            else if(o.getCome()==1){
                o.setOrdermessage("快到预定时间，请就快就餐");
            }
            else if(o.getCome()==2){
                o.setOrdermessage("已到预定时间，我们只能为你额外保留1个小时");
            }
            else{
                o.setOrdermessage("成功就餐");
            }
            list.set(i,o);
        }
        PageInfo<OrderSeatInfo> page = new PageInfo<OrderSeatInfo>(list);
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
    public String delorder(OrderSeatInfo orderSeatInfo, HttpServletRequest request) {
        orderSeatInfo=orderSeatInfoDao.selectById(orderSeatInfo);
        if(orderSeatInfo.getCome()==3){
            return "订单已完成，不能退订";
        }
        else if(orderSeatInfo.getCome()==-1){
            return "订单已取消，不能再取消";
        }
        else if(orderSeatInfo.getCome()==-2){
            return "订单已超时，不能再取消";
        }
        else{
            orderSeatInfo.setCome(-1);
            int num=orderSeatInfoDao.updateCome(orderSeatInfo);
            if(num>0){
                SeatInfo seatInfo=new SeatInfo();
                seatInfo.setSeatId(orderSeatInfo.getSeatId());
                seatInfo=seatInfoDao.selectById(seatInfo);
                String Subject="订座取消信息";
                String Text="你刚才在快捷酒店的订座已被取消。"
                        +"\n"
                        +"订单信息如下："
                        +"\n"
                        +"预定时间："
                        +orderSeatInfo.getTime()
                        +"\n"
                        +"预定位置："
                        +seatInfo.getSeatName()
                        +"\n如果不是本人操作请将尽快联系本店工作人员";
                sendEmail(Subject,Text,request);
                return "取消成功！";
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> selectByseatId(OrderSeatInfo orderSeatInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        HttpSession session=request.getSession();
        if(session.getAttribute("seatId")!=null) {
            int seatId=(int)session.getAttribute("seatId");
            orderSeatInfo.setSeatId(seatId);
        }
        else {
            session.setAttribute("seatId", orderSeatInfo.getSeatId());
        }
        PageHelper.startPage(orderSeatInfo.getPage(),orderSeatInfo.getRow());
        List<OrderSeatInfo> list = new ArrayList<OrderSeatInfo>();
        list = orderSeatInfoDao.selectByseatIdNot(orderSeatInfo);
        for(int i=0;i<list.size();i++){
            OrderSeatInfo o=list.get(i);
            SeatInfo seatInfo=new SeatInfo();
            seatInfo.setSeatId(o.getSeatId());
            seatInfo=seatInfoDao.selectById(seatInfo);
            o.setSeatName(seatInfo.getSeatName());
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(o.getUserId());
            userInfo= userInfoDao.selectById(userInfo);
            o.setUserName(userInfo.getName());
            if(o.getCome()==0){
                o.setOrdermessage("暂未就餐");
            }
            else if(o.getCome()==-1){
                o.setOrdermessage("已经退掉预定");
            }
            else if(o.getCome()==1){
                o.setOrdermessage("快到预定时间，请就快清理客人预定座位");
            }
            else if(o.getCome()==2){
                o.setOrdermessage("已到预定时间，请联系客人尽快就餐");
            }
            else{
                o.setOrdermessage("信息错误！！");
            }
            list.set(i,o);
        }
        PageInfo<OrderSeatInfo> page = new PageInfo<OrderSeatInfo>(list);
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
    public HashMap<String, Object> selectByday(OrderSeatInfo orderSeatInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(orderSeatInfo.getPage(),orderSeatInfo.getRow());
        List<OrderSeatInfo> list = new ArrayList<OrderSeatInfo>();
        list = orderSeatInfoDao.selectBydayNot(orderSeatInfo);
        for(int i=0;i<list.size();i++){
            OrderSeatInfo o=list.get(i);
            SeatInfo seatInfo=new SeatInfo();
            seatInfo.setSeatId(o.getSeatId());
            seatInfo=seatInfoDao.selectById(seatInfo);
            o.setSeatName(seatInfo.getSeatName());
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(o.getUserId());
            userInfo= userInfoDao.selectById(userInfo);
            o.setUserName(userInfo.getName());
            if(o.getCome()==0){
                o.setOrdermessage("暂未就餐");
            }
            else if(o.getCome()==-1){
                o.setOrdermessage("已经退掉预定");
            }
            else if(o.getCome()==1){
                o.setOrdermessage("快到预定时间，请就快清理客人预定座位");
            }
            else if(o.getCome()==2){
                o.setOrdermessage("已到预定时间，请联系客人尽快就餐");
            }
            else if(o.getCome()==-2){
                o.setOrdermessage("用户超时，未就餐");
            }
            else if(o.getCome()==3){
                o.setOrdermessage("用户已就餐");
            }
            else{
                o.setOrdermessage("信息错误！！");
            }
            list.set(i,o);
        }
        PageInfo<OrderSeatInfo> page = new PageInfo<OrderSeatInfo>(list);
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
    public String come(SeatInfo seatInfo) {
        seatInfo=seatInfoDao.selectById(seatInfo);
        if(seatInfo.getUseOrder()==1){
            return "此座位已被预定,暂时不能入座";
        }
        else if(seatInfo.getUseOrder()==2){
            return "此座位已经有人,请确认是否点错";
        }
        else {
            seatInfo.setUseOrder(2);
            int num= seatInfoDao.updateOrder(seatInfo);
            if(num>0){
                return "请带顾客就坐";
            }
        }
        return null;
    }

    @Override
    public String leave(SeatInfo seatInfo) {
        seatInfo=seatInfoDao.selectById(seatInfo);
        if(seatInfo.getUseOrder()==1){
            return "此座位已被预定,请确认是否点错";
        }
        else {
            seatInfo.setUseOrder(0);
            int num= seatInfoDao.updateOrder(seatInfo);
            if(num>0){
                return "请快速清理座位";
            }
        }
        return null;
    }

    public void sendEmail(String Subject,String Text,HttpServletRequest request){
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        String email=userInfo.getEmail();
        session.setAttribute("userinfo",userInfo);
        SimpleMailMessage message = new SimpleMailMessage();
        //设置发件人邮箱
        message.setFrom(sendEmail);
        //设置收件人邮箱
        message.setTo(email);
        //设置邮件主题
        message.setSubject(Subject);
        //设置邮件正文
        message.setText(Text);
        //发送邮件
        javaMailSender.send(message);
    }

}
