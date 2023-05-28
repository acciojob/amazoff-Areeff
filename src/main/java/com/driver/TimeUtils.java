package com.driver;

public class TimeUtils {
    private TimeUtils(){
    }

    public static int  ConvertTime(String deliveryTime){
        String [] time=deliveryTime.split(":");
        return Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
    }
    public static String ConverTime(int deliveryTime){
        int hh=deliveryTime/60;
        int mm=deliveryTime%60;
        String HH=String.valueOf(hh);
        String MM=String.valueOf(mm);
        String time=String.format("%s:%s",HH,MM);
        return time;
    }
}
