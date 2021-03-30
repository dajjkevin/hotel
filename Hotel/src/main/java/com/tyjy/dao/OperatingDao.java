package com.tyjy.dao;

import com.tyjy.pojo.Operating;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperatingDao {
    @Select("SELECT * from operating where back = 0 and department is null")
    List<Operating> select();

    @Select("SELECT * from operating where back = 1 and department is null")
    List<Operating> selectBack();

    @Insert("insert into  operating (supplierId,goodsId,number,goodsPrice) values(#{supplierId},#{goodsId},#{number},#{goodsPrice})")
    int add(Operating operating);

    @Insert("insert into  operating (supplierId,goodsId,number,goodsPrice,back) values(#{supplierId},#{goodsId},#{number},#{goodsPrice},#{back})")
    int del(Operating operating);

    @Insert("insert into  operating (goodsId,number,department) values(#{goodsId},#{number},#{department})")
    int depart(Operating operating);

    @Select("select * from operating where operatingId=#{operatingId}")
    Operating selectById(Operating operating);

    @Select("SELECT * from operating where  department is not null")
    List<Operating> selectDepartment();

    @Select("SELECT count(*) from operating where back=0 and department is null")
    int selectCount();

    @Select("SELECT count(*) from operating where to_days(time) = to_days(now()) and back=0 and department is  null ")
    int selectCountNow();

    @Select("SELECT count(*) from operating WHERE TO_DAYS( NOW() ) - TO_DAYS(time) = 1 and back=0 and department is  null ")
    int selectCountPre();

    @Select("SELECT count(*) from operating where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(time) and back=0 and department is  null ")
    int selectCountWeek();

    @Select("SELECT count(*) from operating WHERE DATE_FORMAT( time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) and back=0 and department is null ")
    int selectCountMouth();

    @Select("SELECT count(*) from operating where back=1 and department is null")
    int selectbackCount();

    @Select("SELECT count(*) from operating where to_days(time) = to_days(now()) and back=1 and department is  null ")
    int selectbackCountNow();

    @Select("SELECT count(*) from operating WHERE TO_DAYS( NOW() ) - TO_DAYS(time) = 1 and back=1 and department is  null ")
    int selectbackCountPre();

    @Select("SELECT count(*) from operating where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(time) and back=1 and department is  null ")
    int selectbackCountWeek();

    @Select("SELECT count(*) from operating WHERE DATE_FORMAT( time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) and back=1 and department is null ")
    int selectbackCountMouth();
}
