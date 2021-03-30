package com.tyjy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyjy.dao.AdminInfoDao;
import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.UserInfo;
import com.tyjy.util.MdFive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class AdminInfoserviceImpl implements AdminInfoservice{

    @Autowired(required = false)
    AdminInfoDao adminInfoDao;

    @Autowired
    MdFive mdFive;


    public void removeSessionAttributr(HttpServletRequest request){
        Enumeration em = request.getSession().getAttributeNames();  //得到session中所有的属性名
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString()); //遍历删除session中的值
        }
    }
    @Override
    public String Login(AdminInfo adminInfo, HttpServletRequest request) {
        AdminInfo admin=adminInfoDao.selectByName(adminInfo);
        if(admin==null){
            return "管理员不存在";
        }
        else {
            String pwd=mdFive.encrypt(adminInfo.getAdminPwd(),admin.getSalt());
            if(pwd.equals(admin.getAdminPwd())){
                HttpSession session=request.getSession();
                session.setAttribute("admin",admin);
                return "登录成功";
            }
            else {
                return "密码错误";
            }
        }
    }

    @Override
    public HashMap<String, Object> select(AdminInfo adminInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(adminInfo.getPage(),adminInfo.getRow());
        List<AdminInfo> list = new ArrayList<>();
        if(adminInfo.getDepartment()==null || adminInfo.getDepartment().equals("")){
            list = adminInfoDao.select();
        }
        else {
            list =adminInfoDao.selectByDepart(adminInfo);
        }
        PageInfo<AdminInfo> page = new PageInfo<AdminInfo>(list);
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
    public List<AdminInfo> selectDepart() {
        return adminInfoDao.selectDepartment();
    }

    @Override
    public String add(AdminInfo adminInfo) {
        int count=adminInfoDao.ValName(adminInfo);
        if(count>0){
            return "该用户名已存在";
        }else {
            Random rd = new Random();
            String salt = rd.nextInt(10000)+"";
            String pwd=mdFive.encrypt("123456",salt);
            adminInfo.setAdminPwd(pwd);
            adminInfo.setSalt(salt);
            int num=adminInfoDao.add(adminInfo);
            if(num>0){
                return "添加成功";
            }
        }
        return "添加失败";
    }

    @Override
    public String editPwd(AdminInfo adminInfo,HttpServletRequest request) {
        HttpSession session =request.getSession();
        AdminInfo admin= (AdminInfo) session.getAttribute("admin");
        String pwd=mdFive.encrypt(adminInfo.getAdminPwd(),admin.getSalt());
        adminInfo.setAdminPwd(pwd);
        adminInfo.setAdminId(admin.getAdminId());
        int num=adminInfoDao.updatePwd(adminInfo);
        if(num>0){
            removeSessionAttributr(request);
            return "修改成功";
        }
        else return "修改失败";
    }

    @Override
    public String Pwd(AdminInfo adminInfo) {
        AdminInfo admin=adminInfoDao.selectById(adminInfo);
        String pwd=mdFive.encrypt(adminInfo.getAdminPwd(),admin.getSalt());
        adminInfo.setAdminPwd(pwd);
        int num=adminInfoDao.updatePwd(adminInfo);
        if(num>0){
            return "修改成功";
        }
        else return "修改失败";
    }
}
