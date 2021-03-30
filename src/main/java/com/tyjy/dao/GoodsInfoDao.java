package com.tyjy.dao;

import com.tyjy.pojo.GoodsInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsInfoDao {
    @Select("SELECT * from goodsinfo ORDER BY goodsNum ASC ")
    List<GoodsInfo> select();

    @Select("SELECT * from goodsinfo where goodsId=#{goodsId}")
    GoodsInfo findGoods(int goodsId);

    @Select("SELECT count(*) from goodsinfo where goodsName=#{goodsName}")
    int selectCountByName(GoodsInfo goodsInfo);

    @Insert("insert into  goodsinfo (goodsName,goodsNum) values(#{goodsName},#{goodsNum})")
    int add(GoodsInfo goodsInfo);

    @Select("SELECT * from goodsinfo where goodsName=#{goodsName}")
    GoodsInfo selectBygoodsName(GoodsInfo goodsInfo);

    @Select("SELECT * from goodsinfo where goodsId=#{goodsId}")
    GoodsInfo selectBygoodsId(GoodsInfo goodsInfo);

    @Update("update goodsinfo set goodsNum=#{goodsNum} where goodsId=#{goodsId}")
    int updategoodsNum(GoodsInfo goodsInfo);
}
