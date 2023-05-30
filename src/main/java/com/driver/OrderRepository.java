package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private Map<String,Order> ordersData=new HashMap<>();
    private Map<String,DeliveryPartner> partnersData=new HashMap<>();
    private Map<String, List<String>> orderPartnerData=new HashMap<>();
    private Map<String,String> orderPartnerPair=new HashMap<>();
    public void addOrder(Order order) {
        ordersData.put(order.getId(),order);
    }

    public void addPartner(DeliveryPartner deliveryPartner) {
        partnersData.put(deliveryPartner.getId(),deliveryPartner);
    }

    public Optional<Order> findOrder(String orderId) {
        if(ordersData.containsKey(orderId)){
            return Optional.of(ordersData.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> findDeliveryPartner(String partnerId) {
        if(partnersData.containsKey(partnerId)){
            return Optional.of(partnersData.get(partnerId));
        }
        return Optional.empty();
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
       // orderPartnerData.put(partnerId,orderId)
        List<String> ordersList=orderPartnerData.getOrDefault(partnerId,new ArrayList<>());
        ordersList.add(orderId);
        orderPartnerData.put(partnerId,ordersList);
        orderPartnerPair.put(orderId,partnerId);
    }

    public List<String> getOrderByPartnerId(String partnerId) {
        return new ArrayList<>(orderPartnerData.getOrDefault(partnerId,new ArrayList<>()));
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(ordersData.keySet());
    }

    public List<String> getAssignedOrder() {
        return new ArrayList<>(orderPartnerPair.keySet());
    }

    public void deletePartner(String partnerId) {
        partnersData.remove(partnerId);
        orderPartnerData.remove(partnerId);
    }

    public void UnassaignOrders(String id) {
        orderPartnerPair.remove(id);
    }

    public void deleteOrder(String orderId) {
        ordersData.remove(orderId);
        orderPartnerPair.remove(orderId);
    }

    public Optional<String> GetPartner(String orderId) {
        if(orderPartnerPair.containsKey(orderId)){
            return Optional.of(orderPartnerPair.get(orderId));
        }
        return Optional.empty();
    }
}
