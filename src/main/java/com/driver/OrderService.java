package com.driver;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Log4j2
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        orderRepository.addPartner(deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Optional<Order> orderOptional=orderRepository.findOrder(orderId);
        Optional<DeliveryPartner> deliveryPartnerOptional=orderRepository.findDeliveryPartner(partnerId);
        if(orderOptional.isEmpty()){
            log.warn("Order not found");
            throw new OrdernotFoundExceeption();
        }
        if(deliveryPartnerOptional.isEmpty()){
            log.warn("Delivery Partner not found");
            throw new DeliveryPartnerNotFound();
        }
        DeliveryPartner deliveryPartner=deliveryPartnerOptional.get();
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        orderRepository.addPartner(deliveryPartner);
       orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        Optional<Order> orderOptional=orderRepository.findOrder(orderId);
        if(orderOptional.isEmpty()){
            throw new OrdernotFoundExceeption();
        }
        return orderOptional.get();
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        Optional<DeliveryPartner> deliveryPartnerOptional=orderRepository.findDeliveryPartner(partnerId);
        if(deliveryPartnerOptional.isEmpty()){
            throw new DeliveryPartnerNotFound();
        }
        return deliveryPartnerOptional.get();
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        List<String> Orders=orderRepository.getOrderByPartnerId(partnerId);
        return Orders.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String>Orders=orderRepository.getOrderByPartnerId(partnerId);
        return Orders;
    }

    public List<String> getAllOrders() {
       return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getAllOrders().size()-orderRepository.getAssignedOrder().size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String> orderIds=orderRepository.getOrderByPartnerId(partnerId);
        List<Order>orders=new ArrayList<>();
        for(String Id:orderIds){
            Order order=orderRepository.findOrder(Id).get();
            if(order.getDeliveryTime()>TimeUtils.ConvertTime(time)){
                orders.add(order);
            }
        }
        return orders.size();

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String>orderIds=orderRepository.getOrderByPartnerId(partnerId);
        int maxTime=0;
        for (String Id:orderIds){
            Order order=orderRepository.findOrder(Id).get();
            if(order.getDeliveryTime()>maxTime){
                maxTime=order.getDeliveryTime();
            }
        }
        return TimeUtils.ConverTime(maxTime);
    }

    public void deletePartnerById(String partnerId) {
        List<String>OrderIds=orderRepository.getOrderByPartnerId(partnerId);
        orderRepository.deletePartner(partnerId);
        for(String id:OrderIds){
            orderRepository.UnassaignOrders(id);
        }
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrder(orderId);
        Optional<String> partner=orderRepository.GetPartner(orderId);
        if(partner.isPresent()){
            List<String>OrderIds=orderRepository.getOrderByPartnerId(partner.get());
            OrderIds.remove(orderId);
        }
    }
}
