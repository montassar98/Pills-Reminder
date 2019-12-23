package com.montassar.distributeurdespliles;



public class Time{

    private int id;
    private String msg;
    private String hour;

    public Time(int id, String msg, String hour) {
        this.id = id;
        this.msg = msg;
        this.hour = hour;
    }

    public Time(String msg, String hour) {
        this.msg = msg;
        this.hour = hour;
    }

    public Time() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


}
