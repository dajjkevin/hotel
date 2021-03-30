package com.tyjy.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface Countservice {

    void allCount(HttpServletRequest request);
    void adminCount(HttpServletRequest request);
    void userCount(HttpServletRequest request);
    void dishCount(HttpServletRequest request);
    void seatCount(HttpServletRequest request);
    void orderNoCount(HttpServletRequest request);
}
