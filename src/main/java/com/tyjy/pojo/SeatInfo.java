package com.tyjy.pojo;


public class SeatInfo extends Mypage {

    private int seatId;
    private String seatName;
    private int UseOrder;
    private String message;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUseOrder() {
        return UseOrder;
    }

    public void setUseOrder(int useOrder) {
        UseOrder = useOrder;
    }
}
