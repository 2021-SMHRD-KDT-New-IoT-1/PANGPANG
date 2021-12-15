package com.cjh.finalproject;

public class DataVO {
    private int img;
    private String number;
    private String blackdate;

    public DataVO() {
    }

    public DataVO(int img, String number, String blackdate) {
        this.img = img;
        this.number = number;
        this.blackdate = blackdate;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBlackdate() {
        return blackdate;
    }

    public void setBlackdate(String blackdate) {
        this.blackdate = blackdate;
    }

}
