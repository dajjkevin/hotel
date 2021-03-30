package com.tyjy.dao;


import com.tyjy.pojo.DishInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishInfoDao {
    @Select("SELECT * from dishinfo")
    List<DishInfo> select(DishInfo dishInfo);


    @Select("SELECT * from dishinfo where department=#{department}")
    List<DishInfo> selectBydepart(DishInfo dishInfo);

    @Select("SELECT * from dishinfo where type=#{type}")
    List<DishInfo> selectBytype(DishInfo dishInfo);

    @Select("SELECT * from dishinfo where dishId=#{dishId} ")
    DishInfo selectByid(DishInfo dishInfo);

    @Insert("insert into  dishinfo (dishName,dishPrice,department,type) values(#{dishName},#{dishPrice},#{department},#{type})")
    int add(DishInfo dishInfo);

    @Select("SELECT count(*) from dishinfo where dishName=#{dishName}")
    int countBydishName(DishInfo dishInfo);

    @Select("SELECT   distinct(type) from dishinfo")
    List<DishInfo> selectType();

    @Select("SELECT   * from  dishinfo ORDER BY dishId DESC limit 5 ")
    List<DishInfo> selectTable();

    @Select("select count(*) from dishinfo ")
    int selectCount();
}
