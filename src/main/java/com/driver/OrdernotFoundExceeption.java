package com.driver;

public class OrdernotFoundExceeption extends RuntimeException {
    public OrdernotFoundExceeption(){
        super("Order not found in the map");
    }
}
