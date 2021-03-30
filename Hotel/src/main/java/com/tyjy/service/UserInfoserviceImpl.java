package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.UserInfoDao;
import com.tyjy.pojo.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserInfoserviceImpl implements UserInfoservice {

    @Autowired(required = false)
    UserInfoDao userInfoDao;


    @Override
    public HashMap<String, Object> select(UserInfo user, HttpServletRequest request) {
        HttpSession session=request.getSession();
        int num=1;
        if(session.getAttribute("sort")!=null) {
            num = (int) session.getAttribute("sort");
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(user.getPage(),user.getRow());
        List<UserInfo> list = new ArrayList<>();
        //判断用户输入的查询条件是否有值
        if(user.getConValue()==null){
            if(num ==1){
                list  = userInfoDao.selectDESC();
            }
            else if(num==2){
                list  = userInfoDao.selectASC();
            }
            else {
                list  = userInfoDao.select();
            }
        }else{
            if(user.getCondition().equals("编号")){
                //设置用户输入的查询条件
                user.setUserId(Integer.parseInt(user.getConValue()));
                list = userInfoDao.findByUserId(user);
            }else if(user.getCondition().equals("用户名")){
                user.setUserName(user.getConValue());
                list = userInfoDao.findByUserName(user);
            }
            else if(user.getCondition().equals("姓名")){
                user.setName(user.getConValue());
                list = userInfoDao.findByName(user);
            }else{
                if(num ==1){
                    list  = userInfoDao.selectDESC();
                }
                else if(num==2){
                    list  = userInfoDao.selectASC();
                }
                else {
                    list  = userInfoDao.select();
                }
            }
        }

        PageInfo<UserInfo> page = new PageInfo<UserInfo>(list);
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
    public String del(UserInfo userInfo) {
        int d=userInfoDao.del(userInfo);
        if(d>0){
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public String editinfo(UserInfo userInfo, HttpServletRequest request) {
        UserInfo user=userInfoDao.selectById(userInfo);
        if(user!=null){
            HttpSession session=request.getSession();
            session.setAttribute("editinfo",user);
            return "yes";
        }
        else {
            return "no";
        }
    }

    @Override
    public String edit(UserInfo userInfo, HttpServletRequest request) {
        if(userInfo!=null){
        int num=userInfoDao.update(userInfo);
        if(num>0){
            HttpSession session =request.getSession();
            session.setAttribute("userinfo",userInfo);
            return "修改成功";
        }
        else return "修改失败";}
        else {
            return "失败";
        }
    }

    @Override
    public List<UserInfo> selecttable() {
        return userInfoDao.selecttabel();
    }


}
