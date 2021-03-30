package com.tyjy.dao;


import com.tyjy.pojo.SeatInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SeatInfoDao {
    @Select("SELECT * from seatInfo ")
    List<SeatInfo> select();

    @Select("select * from seatInfo where seatId =#{seatId}")
    SeatInfo selectById(SeatInfo seatInfo);

    @Update("update seatInfo set UseOrder= #{UseOrder} where seatId=#{seatId}")
    int updateOrder(SeatInfo seatInfo);

    @Select("select count(*) from seatInfo where UseOrder = 0")
    int selectCount();
}
