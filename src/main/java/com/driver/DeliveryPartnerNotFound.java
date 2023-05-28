package com.driver;

public class DeliveryPartnerNotFound extends RuntimeException {
    public DeliveryPartnerNotFound(){
        super("DeliveryPartner Not Found in the map");
    }
}
