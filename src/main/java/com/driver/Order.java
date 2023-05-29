package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Order() {
    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        this.deliveryTime=TimeUtils.ConvertTime(deliveryTime);
    }

//    public String getId() {
//        return id;
//    }
//
//    public int getDeliveryTime() {return deliveryTime;}
}
