package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.GoodsInfoDao;
import com.tyjy.dao.OperatingDao;
import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.Operating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodsInfoserviceImpl implements GoodsInfoservice{

    @Autowired(required = false)
    GoodsInfoDao goodsInfoDao;


    @Override
    public HashMap<String, Object> select(GoodsInfo goodsInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(goodsInfo.getPage(),goodsInfo.getRow());
        List<GoodsInfo> list = new ArrayList<>();
        list = goodsInfoDao.select();
        for(int i = 0; i < list.size(); i++){
            GoodsInfo goods=(GoodsInfo) list.get(i);
            if(goods.getGoodsNum()<=100){
                goods.setMessage("马上没货,尽快联系供货商");
                list.set(i,goods);
            }
        }
        PageInfo<GoodsInfo> page = new PageInfo<GoodsInfo>(list);
        map.put("list",page.getList());
        map.put("total",page.getTotal());
        map.put("totalPage",page.getPages());
        if(page.getPrePage()==0){
            map.put("prePage",1);
        }
        else {
            map.put("prePage", page.getPrePage());
        }
        if(page.getNextPage()==0){
            map.put("nextPage",page.getPages());
        }
        else {
            map.put("nextPage",page.getNextPage());
        }
        map.put("cur",page.getPageNum());
        return map;
    }

    @Override
    public String departSession(GoodsInfo goodsInfo, HttpServletRequest request) {
        HttpSession session=request.getSession();
        GoodsInfo g=goodsInfoDao.selectBygoodsId(goodsInfo);
        if(g!=null){
            session.setAttribute("departGoods",g);
            return "yes";
        }
        else
            return "no";
    }

}
