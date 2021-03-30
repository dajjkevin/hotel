package com.tyjy.dao;

import com.tyjy.pojo.OrderInfo;
import com.tyjy.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderInfoDao {
    @Select("SELECT * from orderInfo where pay =1")
    List<OrderInfo> selectPayedAdmin();

    @Select("SELECT * from orderInfo where pay =0")
    List<OrderInfo> selectPayAdmin();

    @Select("SELECT * from orderInfo where pay =0 and UserId=#{UserId}")
    List<OrderInfo> selectPay(OrderInfo orderInfo);

    @Select("SELECT * from orderInfo where pay =1 and UserId=#{UserId}")
    List<OrderInfo> selectPayed(OrderInfo orderInfo);

    @Select("SELECT distinct(payNo) from orderInfo where pay =1 and UserId=#{UserId}")
    List<OrderInfo> selectPayNo(OrderInfo orderInfo);

    @Select("SELECT * from orderInfo where UserId =#{UserId} and pay=0")
    List<OrderInfo> selectByUserId(OrderInfo orderInfo);

    @Select("select count(*) from orderInfo where pay=0 and dishId=#{dishId} and UserId=#{UserId}")
    int selectCountNumber(OrderInfo orderInfo);

    @Select("select * from orderInfo where pay=0 and dishId=#{dishId}")
    OrderInfo selectBydish(OrderInfo orderInfo);

    @Insert("insert into  orderinfo (dishId,UserId,number) values(#{dishId},#{UserId},#{number})")
    int add(OrderInfo orderInfo);

    @Update("update orderinfo set pay= 1,payNo=#{payNo} where UserId=#{UserId} and pay =0  ")
    int pay(OrderInfo orderInfo);

    @Update("update orderinfo set number = #{number} where UserId=#{UserId} and pay=0 and dishId=#{dishId}")
    int updatenumber(OrderInfo orderInfo);

    @Select("SELECT * from orderInfo where pay =1 and to_days(time) = to_days(now())")
    List<OrderInfo> selectPayedNow();

    @Select("SELECT * from orderInfo where pay =1 and TO_DAYS( NOW() ) - TO_DAYS(time) = 1")
    List<OrderInfo> selectPayedPre();

    @Select("SELECT * from orderInfo where pay =1 and DATE_SUB(NOW(), INTERVAL 7 day) <= date(time)")
    List<OrderInfo> selectPayedweek();

    @Select("SELECT * from orderInfo where pay =1 and DATE_FORMAT( time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )")
    List<OrderInfo> selectPayedMouth();

    @Select("select * from orderInfo where payNo=#{payNo}")
    List<OrderInfo> selectByPayNo(OrderInfo orderInfo);

    @Select("select count(*) from orderInfo where pay=0")
    int selectCountPay();
}
