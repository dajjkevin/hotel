package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.GoodsInfoDao;
import com.tyjy.dao.OperatingDao;
import com.tyjy.dao.SupplierInfoDao;
import com.tyjy.pojo.GoodsInfo;
import com.tyjy.pojo.Operating;
import com.tyjy.pojo.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OperatingserviceImpl implements Operatingservice{

    @Autowired(required = false)
    OperatingDao operatingDao;

    @Autowired(required = false)
    GoodsInfoDao goodsInfoDao;

    @Autowired(required = false)
    SupplierInfoDao supplierInfoDao;

    @Override
    public HashMap<String, Object> select(Operating operating, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(operating.getPage(),operating.getRow());
        List<Operating> list = new ArrayList<>();
        list = operatingDao.select();
        for(int i = 0; i < list.size(); i++){
            Operating o=list.get(i);
            SupplierInfo supplierInfo=supplierInfoDao.findSupplier(o.getSupplierId());
            GoodsInfo goodsInfo=goodsInfoDao.findGoods(o.getGoodsId());
            o.setSupplierTel(supplierInfo.getTel());
            o.setSupplierName(supplierInfo.getName());
            o.setGoodsName(goodsInfo.getGoodsName());
            list.set(i,o);
        }
        PageInfo<Operating> page = new PageInfo<Operating>(list);
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
    public HashMap<String, Object> selectback(Operating operating, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(operating.getPage(),operating.getRow());
        List<Operating> list = new ArrayList<>();
        list = operatingDao.selectBack();
        for(int i = 0; i < list.size(); i++){
            Operating o=list.get(i);
            SupplierInfo supplierInfo=supplierInfoDao.findSupplier(o.getSupplierId());
            GoodsInfo goodsInfo=goodsInfoDao.findGoods(o.getGoodsId());
            o.setSupplierTel(supplierInfo.getTel());
            o.setSupplierName(supplierInfo.getName());
            o.setGoodsName(goodsInfo.getGoodsName());
            list.set(i,o);
        }
        PageInfo<Operating> page = new PageInfo<Operating>(list);
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
    public HashMap<String, Object> selectDepartment(Operating operating, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(operating.getPage(),operating.getRow());
        List<Operating> list = new ArrayList<>();
        list = operatingDao.selectDepartment();
        for(int i = 0; i < list.size(); i++){
            Operating o=list.get(i);
            GoodsInfo goodsInfo=goodsInfoDao.findGoods(o.getGoodsId());
            o.setGoodsName(goodsInfo.getGoodsName());
            list.set(i,o);
        }
        PageInfo<Operating> page = new PageInfo<Operating>(list);
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
    public String add(Operating operating) {
        SupplierInfo supplierInfo=new SupplierInfo();
        GoodsInfo goodsInfo=new GoodsInfo();
        supplierInfo.setName(operating.getSupplierName());
        supplierInfo.setTel(operating.getSupplierTel());
        goodsInfo.setGoodsName(operating.getGoodsName());
        int count =supplierInfoDao.selectcount(supplierInfo);
        if(count==0){
            supplierInfoDao.add(supplierInfo);
        }
        count =goodsInfoDao.selectCountByName(goodsInfo);
        if(count==0){
            goodsInfo.setGoodsNum(operating.getNumber());
            goodsInfoDao.add(goodsInfo);
        }
        GoodsInfo g=goodsInfoDao.selectBygoodsName(goodsInfo);
        double goodsNum=g.getGoodsNum()+operating.getNumber();
        g.setGoodsNum(goodsNum);
        int num=goodsInfoDao.updategoodsNum(g);
        if(num>0){
            SupplierInfo s=supplierInfoDao.selectToOperating(supplierInfo);
            operating.setGoodsId(g.getGoodsId());
            operating.setSupplierId(s.getSupplierId());
            num=operatingDao.add(operating);
            if(num>0)
                return "进货成功";
        }
        return "进货失败";
    }

    @Override
    public String del(Operating operating,HttpServletRequest request) {
        HttpSession session=request.getSession();
        Operating o=(Operating)session.getAttribute("delOperating");
        double number=operating.getNumber();
        if(number>o.getNumber()){
            return "退货数目不能超过进货数目";
        }
        GoodsInfo goodsInfo=new GoodsInfo();
        goodsInfo.setGoodsId(o.getGoodsId());
        GoodsInfo g=goodsInfoDao.selectBygoodsId(goodsInfo);
        if(number>g.getGoodsNum()){
            return "退货数目不能超过库存数目";
        }else{
            number=g.getGoodsNum()-number;
            g.setGoodsNum(number);
            int num=goodsInfoDao.updategoodsNum(g);
            if(num>0){
                operating.setGoodsId(o.getGoodsId());
                operating.setGoodsPrice(o.getGoodsPrice());
                operating.setSupplierId(o.getSupplierId());
                operating.setBack(1);
                num=operatingDao.del(operating);
                if(num>0)
                    return "退货成功";
            }
        }
        return "退货失败";
    }

    @Override
    public String delSession(Operating operating,HttpServletRequest request) {
        Operating o=operatingDao.selectById(operating);
        if(o!=null){
            HttpSession session=request.getSession();
            session.setAttribute("delOperating",o);
            return "yes";
        }
        return "no";
    }

    @Override
    public String depart(Operating operating, HttpServletRequest request) {
        HttpSession session=request.getSession();
        GoodsInfo goodsInfo=(GoodsInfo) session.getAttribute("departGoods");
        if(goodsInfo.getGoodsNum()<operating.getNumber()){
            return "取货数目不能超过仓库总数";
        }
        else {
            double number=goodsInfo.getGoodsNum()-operating.getNumber();
            goodsInfo.setGoodsNum(number);
            int num=goodsInfoDao.updategoodsNum(goodsInfo);
            if(num>0){
                operating.setGoodsId(goodsInfo.getGoodsId());
                num=operatingDao.depart(operating);
                if(num>0){
                    return "取货成功";
                }
            }
        }
        return "取货失败";
    }
}
