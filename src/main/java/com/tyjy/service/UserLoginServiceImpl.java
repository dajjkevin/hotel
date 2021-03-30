package com.tyjy.service;

import com.tyjy.dao.UserInfoDao;
import com.tyjy.pojo.UserInfo;
import com.tyjy.util.MdFive;
import com.tyjy.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;

@Service
public class UserLoginServiceImpl implements UserLoginservice {
    @Autowired(required = false)
    UserInfoDao userInfoDao;

    @Autowired(required = false)
    MdFive mdFive;

    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;

    //创建发送邮箱的对象
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String login(UserInfo user, HttpServletRequest request) {
        UserInfo userInfo=userInfoDao.selectByName(user.getUserName());
        if(userInfo!=null){
            String pwd=mdFive.encrypt(user.getUserPwd(),userInfo.getSalt());
            if(pwd.equals(userInfo.getUserPwd())){
                HttpSession session = request.getSession();
                session.setAttribute("userinfo",userInfo);
                String level="普通";
                    if (userInfo.getIntegral() <= 100) {
                        level = "普通";
                    }else if (userInfo.getIntegral() > 100) {
                        level = "黄金";
                    }else if (userInfo.getIntegral() > 2000) {
                        level = "白金";
                    } else if (userInfo.getIntegral() > 5000) {
                        level = "砖石";
                    } else {
                        level = "顶级";
                    }
                session.setAttribute("level",level);
                if(userInfo.getEmail()==null){
                    return "未绑定邮箱";
                }
                else {
                    return "登录成功";
                }
            }
            else {

                if(redisUtil.hasKey(userInfo.getUserName()+","+userInfo.getUserPwd())){
                    Object object=redisUtil.get(userInfo.getUserName()+","+userInfo.getUserPwd());
                    int num=Integer.parseInt(object.toString());
                    if(num<3){
                        redisUtil.incr(userInfo.getUserName()+","+userInfo.getUserPwd(),1);
                        redisUtil.expire(userInfo.getUserName()+","+userInfo.getUserPwd(),60);
                        num=3-num;
                        return "密码错误,还有"+num+"次机会";
                    }
                    else {
                        return "密码错误次数超过3次，请60秒后重试";
                    }
                }
                else {
                    redisUtil.set(userInfo.getUserName()+","+userInfo.getUserPwd(),1,60);
                    return "密码错误,还有3次机会";
                }
            }
        }
        return "用户名输入错误";
    }

    @Override
    public String signin(UserInfo userInfo,HttpServletRequest request) {
        int count=userInfoDao.valName(userInfo);
        if(count>0){
            return "账户名已存在";
        }
        else {
            Random rd = new Random();
            String salt = rd.nextInt(10000)+"";
            String pwd =mdFive.encrypt(userInfo.getUserPwd(),salt);
            userInfo.setSalt(salt);
            userInfo.setUserPwd(pwd);
            int num=userInfoDao.zhuce(userInfo);
            if(num>0){
                HttpSession session = request.getSession();
                session.setAttribute("Newuserinfo",userInfo);
                return "注册成功";
            }
        }
        return "注册失败";
    }

    @Override
    public HashMap<String, Object> sendCode(String email, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int num=userInfoDao.valEmail(email);
        if(num==0){
            map.put("info","该邮箱未注册,前往注册");
        }
        else {
            try {
                //从session中获取当前用户信息
                HttpSession session = request.getSession();
                //创建普通邮件对象
                SimpleMailMessage message = new SimpleMailMessage();
                //设置发件人邮箱
                message.setFrom(sendEmail);
                //设置收件人邮箱
                message.setTo(email);
                //设置邮件主题
                message.setSubject("快捷酒店登录验证码");
                // 生成随机验证码
                Random random = new Random();
                StringBuffer valSb = new StringBuffer();
                String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                int charLength = charStr.length();

                for (int i = 0; i < 5; i++) {
                    int index = random.nextInt(charLength);
                    valSb.append(charStr.charAt(index));
                }
                String valCode = valSb.toString();
                //设置邮件正文
                message.setText("你的验证码是" + valCode);
                System.out.println(valCode);
                //发送邮件
                javaMailSender.send(message);
                //发送成功
                //把验证码存入session中
                session.setAttribute("valCode", valCode);
                session.setAttribute("email", email);
                redisUtil.set(valCode,valCode,60);
                map.put("info", "发送成功");
                return map;

            } catch (Exception e) {
                System.out.println("发送邮件时发生异常！");
                e.printStackTrace();
            }
            map.put("info","发送邮件异常");
        }
        return map;
    }

    @Override
    public HashMap<String, Object> sendcheckCode(UserInfo userInfo, HttpServletRequest request) {
        UserInfo user = userInfoDao.selectByName(userInfo.getUserName());
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (user == null) {
            map.put("info", "用户不存在");
        } else {
            try {
                //从session中获取当前用户信息
                HttpSession session = request.getSession();
                //创建普通邮件对象
                SimpleMailMessage message = new SimpleMailMessage();
                //设置发件人邮箱
                message.setFrom(sendEmail);
                //设置收件人邮箱
                message.setTo(user.getEmail());
                //设置邮件主题
                message.setSubject("快捷酒店登录验证码");
                // 生成随机验证码
                Random random = new Random();
                StringBuffer valSb = new StringBuffer();
                String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                int charLength = charStr.length();

                for (int i = 0; i < 5; i++) {
                    int index = random.nextInt(charLength);
                    valSb.append(charStr.charAt(index));
                }
                String valCode = valSb.toString();
                //设置邮件正文
                message.setText("你的验证码是" + valCode);
                System.out.println(valCode);
                //发送邮件
                javaMailSender.send(message);
                //发送成功
                //把验证码存入session中
                session.setAttribute("checkvalCode", valCode);
                session.setAttribute("changeuser",user);
                redisUtil.set(valCode,valCode,60);
                map.put("info", "发送成功");
                return map;

            } catch (Exception e) {
                System.out.println("发送邮件时发生异常！");
                e.printStackTrace();
            }
            map.put("info", "发送邮件异常");

        }
        return map;
    }

    @Override
    public HashMap<String, Object> emailCode(UserInfo userInfo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int num=userInfoDao.valEmail(userInfo.getEmail());
        if(num>0){
            map.put("info","该邮箱已绑定");
        }
        else {
            try {
                //从session中获取当前用户信息
                HttpSession session = request.getSession();
                UserInfo user=(UserInfo)session.getAttribute("Newuserinfo");
                user.setEmail(userInfo.getEmail());
                session.setAttribute("Newuserinfo",user);
                //创建普通邮件对象
                SimpleMailMessage message = new SimpleMailMessage();
                //设置发件人邮箱
                message.setFrom(sendEmail);
                //设置收件人邮箱
                message.setTo(user.getEmail());
                //设置邮件主题
                message.setSubject("快捷酒店登录验证码");
                // 生成随机验证码
                Random random = new Random();
                StringBuffer valSb = new StringBuffer();
                String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                int charLength = charStr.length();

                for (int i = 0; i < 5; i++) {
                    int index = random.nextInt(charLength);
                    valSb.append(charStr.charAt(index));
                }
                String valCode = valSb.toString();
                //设置邮件正文
                message.setText("你的验证码是" + valCode);
                redisUtil.set(valCode,valCode,60);
                System.out.println(valCode);
                //发送邮件
                javaMailSender.send(message);
                //发送成功
                //把验证码存入session中
                session.setAttribute("emailvalCode", valCode);
                map.put("info", "发送成功");
                return map;

            } catch (Exception e) {
                System.out.println("发送邮件时发生异常！");
                e.printStackTrace();
            }
            map.put("info", "发送邮件异常");
        }
        return map;
    }

    @Override
    public int email(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo user=(UserInfo)session.getAttribute("Newuserinfo");
        int num=userInfoDao.updateEmail(user);
        if(num>0)
        return 1;
        else return 0;
    }

    @Override
    public String changePwd(UserInfo userInfo, HttpServletRequest request) {
        HttpSession session=request.getSession();
        UserInfo user=(UserInfo) session.getAttribute("changeuser");
        String pwd=mdFive.encrypt(userInfo.getUserPwd(),user.getSalt());
        user.setUserPwd(pwd);
        int num=userInfoDao.updatePwd(user);
        if(num>0){
            return "修改成功";
        }
        else return "修改失败";
    }

    @Override
    public String emailLogin( HttpServletRequest request) {
        HttpSession session=request.getSession();
        String email=(String)session.getAttribute("email");
        UserInfo u=userInfoDao.selectByEmail(email);
        if(u!=null){
            session.setAttribute("userinfo",u);
            return "登陆成功";
        }
        return "登陆失败";
    }
}