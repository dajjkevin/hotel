package com.tyjy.dao;


import com.tyjy.pojo.OrderInfo;
import com.tyjy.pojo.OrderSeatInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.core.annotation.Order;

import java.util.List;

@Mapper
public interface OrderSeatInfoDao {

    @Select("SELECT count(*) from orderseat where   DATE_SUB((#{time}), INTERVAL 3 hour) <= time and DATE_SUB((#{time}), INTERVAL 0 hour) >= time ")
    int selectseatAftertime(OrderSeatInfo orderSeatInfo);

    @Select("SELECT count(*) from orderseat where   DATE_SUB((#{time}), INTERVAL -3 hour) >= time and DATE_SUB((#{time}), INTERVAL 0 hour) <= time ")
    int selectseatBeforetime(OrderSeatInfo orderSeatInfo);

    @Insert("insert into  orderseat (seatId,message,time,UserId) values(#{seatId},#{message},#{time},#{UserId})")
    int order(OrderSeatInfo orderSeatInfo);

    @Select("select * from orderseat where DATE_SUB((#{time}), INTERVAL -2 hour) >= time and DATE_SUB((#{time}), INTERVAL 0 hour) <= time")
    List<OrderSeatInfo> selectorderChange(String time);

    @Select("select * from orderseat where DATE_SUB((#{time}), INTERVAL 1 hour) >= time  and come=2")
    List<OrderSeatInfo> selectorderPassed(String time);

    @Select("select * from orderseat where DATE_SUB((#{time}), INTERVAL 1 hour) <= time and DATE_SUB((#{time}), INTERVAL 0 hour) >= time and come=1")
    List<OrderSeatInfo> selectorderWait(String time);

    @Update("update orderseat set come = #{come} where orderSeatId=#{orderSeatId}")
    int updateCome(OrderSeatInfo orderSeatInfo);

    @Select("select * from orderseat where UserId=#{UserId}")
    List<OrderSeatInfo> selectByUser(OrderSeatInfo orderSeatInfo);

    @Select("select * from orderseat  where seatId=#{seatId} and( come =1 or come = 0 or come=2 or come=-1) ORDER BY time ASC")
    List<OrderSeatInfo> selectByseatIdNot(OrderSeatInfo orderSeatInfo);

    @Select("select * from orderseat where  to_days(time) = to_days(now())")
    List<OrderSeatInfo> selectBydayNot(OrderSeatInfo orderSeatInfo);

    @Select("select * from orderseat where orderSeatId=#{orderSeatId}")
    OrderSeatInfo selectById(OrderSeatInfo orderSeatInfo);
}
