package com.tyjy.dao;

import com.tyjy.pojo.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserInfoDao {

    @Select("select * from userinfo where UserName=#{UserName}")
    UserInfo selectByName(String userName);

    @Select("select count(*) from userinfo where UserName=#{UserName}")
    int valName(UserInfo userInfo);

    @Insert("insert into userinfo (UserName,UserPwd,salt) values(#{UserName},#{UserPwd},#{salt})")
    int zhuce(UserInfo userInfo);

    @Select("select * from userinfo where Email=#{Email}")
    UserInfo selectByEmail(String Email);

    @Select("select * from userinfo where UserId=#{UserId}")
    UserInfo selectById(UserInfo userInfo);


    @Select("select count(*) from userinfo where Email=#{Email}")
    int valEmail(String Email);

    @Update("update userInfo set UserPwd=#{UserPwd} where UserId=#{UserId}")
    int updatePwd(UserInfo user);

    @Update("update userInfo set Email=#{Email} where UserName=#{UserName}")
    int updateEmail(UserInfo user);

    @Select("SELECT * from userinfo ORDER BY integral DESC")
    List<UserInfo> selectDESC();

    @Select("SELECT * from userinfo ORDER BY integral ASC")
    List<UserInfo> selectASC();

    @Select("SELECT * from userinfo ")
    List<UserInfo> select();

    @Select("SELECT * FROM userinfo WHERE UserId=#{UserId} ")
    List<UserInfo> findByUserId(UserInfo userInfo);

    @Select("select * from userinfo where UserName=#{UserName} ")
    List<UserInfo> findByUserName(UserInfo user);

    @Select("select * from userinfo where Name=#{Name} ")
    List<UserInfo> findByName(UserInfo user);

    @Delete("delete from userInfo where UserId=#{UserId} ")
    int del(UserInfo userInfo);

    @Update("update userInfo set UserName=#{UserName},Name=#{Name},Age=#{Age},Address=#{Address},Sex=#{Sex} where UserId=#{UserId}")
    int update(UserInfo user);

    @Select("SELECT count(*) from userinfo ")
    int selectCount();

    @Select("SELECT count(*) from  userinfo  where to_days(time) = to_days(now()) ")
    int selectCountNow();

    @Select("SELECT count(*) from  userinfo  WHERE TO_DAYS( NOW() ) - TO_DAYS(time) = 1 ")
    int selectCountPre();

    @Select("SELECT count(*) from userinfo where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(time)")
    int selectCountWeek();

    @Select("SELECT count(*) from userinfo  WHERE DATE_FORMAT( time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )")
    int selectCountMouth();

    @Update("update userinfo set integral =#{integral} where UserId=#{UserId}")
    int updateinf(UserInfo userInfo);

    @Select("select * from userinfo ORDER BY UserId DESC limit 8")
    List<UserInfo> selecttabel();
}
