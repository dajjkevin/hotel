package com.tyjy.pojo;

public class OrderSeatInfo extends Mypage{
    private int orderSeatId;
    private int seatId;
    private int UserId;
    private String  UserName;
    private String seatName;
    private String message;
    private String time;
    private int come;
    private String ordermessage;

    public int getOrderSeatId() {
        return orderSeatId;
    }

    public void setOrderSeatId(int orderSeatId) {
        this.orderSeatId = orderSeatId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getCome() {
        return come;
    }

    public void setCome(int come) {
        this.come = come;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getOrdermessage() {
        return ordermessage;
    }

    public void setOrdermessage(String ordermessage) {
        this.ordermessage = ordermessage;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }
}
