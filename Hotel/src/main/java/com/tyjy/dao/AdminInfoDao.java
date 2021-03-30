package com.tyjy.dao;

import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminInfoDao {
    @Select("select * from admininfo where AdminName=#{AdminName}")
    AdminInfo selectByName(AdminInfo adminInfo);

    @Select("select * from admininfo where AdminId=#{AdminId}")
    AdminInfo selectById(AdminInfo adminInfo);

    @Select("SELECT * from admininfo ")
    List<AdminInfo> select();

    @Select("SELECT count(*) from admininfo ")
    int selectCount();

    @Select("SELECT count(*) from admininfo where to_days(time) = to_days(now()) ")
    int selectCountNow();

    @Select("SELECT count(*) from admininfo WHERE TO_DAYS( NOW() ) - TO_DAYS(time) = 1 ")
    int selectCountPre();

    @Select("SELECT count(*) from admininfo where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(time)")
    int selectCountWeek();

    @Select("SELECT count(*) from admininfo WHERE DATE_FORMAT( time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )")
    int selectCountMouth();

    @Select("SELECT * from admininfo where department=#{department} ")
    List<AdminInfo> selectByDepart(AdminInfo adminInfo);

    @Select("SELECT   distinct(department) from admininfo")
    List<AdminInfo> selectDepartment();

    @Insert("insert into admininfo (AdminName,AdminPwd,salt,department,Name,leader) values(#{AdminName},#{AdminPwd},#{salt},#{department},#{Name},#{leader})")
    int add(AdminInfo adminInfo);

    @Select("SELECT count(*) from admininfo where AdminName=#{AdminName}")
    int ValName(AdminInfo adminInfo);

    @Update("update admininfo set AdminPwd=#{AdminPwd} where AdminId=#{AdminId}")
    int updatePwd(AdminInfo adminInfo);
}
