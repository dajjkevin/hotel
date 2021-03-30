package com.tyjy.dao;

import com.tyjy.pojo.PayMent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayMentDao {

    @Insert("insert into payment(payNo,tradeNo,buyerId,subject,payTotal,buyer_pay_amount,time)values(#{payNo},#{tradeNo},#{buyerId},#{subject},#{payTotal},#{buyer_pay_amount},#{time})")
    int add(PayMent payMent);
}
