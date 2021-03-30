package com.tyjy.util;

import com.tyjy.pojo.AdminInfo;
import com.tyjy.pojo.UserInfo;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class Adminfilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpSession session=request.getSession();
        AdminInfo adminInfo=(AdminInfo) session.getAttribute("admin");
        String url=request.getRequestURI()+"";
        if(url.equals("/admin/xadmin")){
            if(adminInfo!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }
            else {
                request.getRequestDispatcher("/adminloginPage").forward(servletRequest,servletResponse);
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
