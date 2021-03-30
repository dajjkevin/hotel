package com.tyjy.util;

import com.tyjy.pojo.UserInfo;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class Userfilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        String url=request.getRequestURI()+"";
        if(url.equals("/index")){
            if(userInfo!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }
            else {
                request.getRequestDispatcher("/user/loginPage").forward(servletRequest,servletResponse);
            }
        }
        else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
