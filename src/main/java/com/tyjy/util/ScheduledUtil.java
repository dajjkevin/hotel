package com.tyjy.util;

import com.tyjy.dao.OrderSeatInfoDao;
import com.tyjy.dao.SeatInfoDao;
import com.tyjy.pojo.OrderSeatInfo;
import com.tyjy.pojo.SeatInfo;
import com.tyjy.service.SeatInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledUtil {

    @Autowired(required = false)
    SeatInfoDao seatInfoDao;

    @Autowired(required = false)
    OrderSeatInfoDao orderSeatInfoDao;

    @Autowired
    SeatInfoservice seatInfoservice;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void configureTasks() {
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为: " + ft.format(dNow)+"");
        orderchange(ft.format(dNow)+"");
        orderPassed(ft.format(dNow)+"");
        System.out.println("---自动刷新---");
    }

    public void orderchange(String time){
        List<OrderSeatInfo> list= new ArrayList<OrderSeatInfo>();
        list=orderSeatInfoDao.selectorderChange(time);
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                OrderSeatInfo orderSeatInfo = list.get(i);
                SeatInfo seatInfo = new SeatInfo();
                seatInfo.setSeatId(orderSeatInfo.getSeatId());
                seatInfo.setUseOrder(1);
                orderSeatInfo.setCome(1);
                int num1 = orderSeatInfoDao.updateCome(orderSeatInfo);
                int num2 = seatInfoDao.updateOrder(seatInfo);
                if(num1>0&&num2>0){
                    System.out.println("————有座位信息更新————");
                }
            }
        }
        list.clear();
        list=orderSeatInfoDao.selectorderWait(time);
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                OrderSeatInfo orderSeatInfo = list.get(i);
                orderSeatInfo.setCome(2);
                int num = orderSeatInfoDao.updateCome(orderSeatInfo);
                if(num>0){
                    System.out.println("————有座位信息更新————");
                }
            }
        }
    }

    public void orderPassed(String time){
        List<OrderSeatInfo> list= new ArrayList<OrderSeatInfo>();
        list=orderSeatInfoDao.selectorderPassed(time);
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                OrderSeatInfo orderSeatInfo = list.get(i);
                orderSeatInfo.setCome(-2);
                SeatInfo seatInfo = new SeatInfo();
                seatInfo.setSeatId(orderSeatInfo.getSeatId());
                seatInfo.setUseOrder(0);
                int num1 = orderSeatInfoDao.updateCome(orderSeatInfo);
                int num2 = seatInfoDao.updateOrder(seatInfo);
                if(num1>0&&num2>0){
                    System.out.println("-----有顾客未按时到达已经将座位释放-----");
                }
            }
        }
    }
}

