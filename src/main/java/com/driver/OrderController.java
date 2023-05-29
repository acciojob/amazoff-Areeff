package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
     @Autowired
     private OrderService orderService;
    @PostMapping("/add-order")
    public ResponseEntity<String> AddOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> AddPartner(@PathVariable String partnerId){
        orderService.addPartner(partnerId);
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> AddOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){
        try{
            orderService.addOrderPartnerPair(orderId,partnerId);
            return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
        }catch (OrdernotFoundExceeption ex){
            return new ResponseEntity<>("Order not found",HttpStatus.NOT_FOUND);
        }catch (DeliveryPartnerNotFound ex){
            return new ResponseEntity<>("partner not found",HttpStatus.NOT_FOUND);
        }catch (RuntimeException ex){
            return new ResponseEntity<>("other exception",HttpStatus.NOT_FOUND);
        }
        //This is basically assigning that order to that partnerId

    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> GetOrderById(@PathVariable String orderId){
       try {
           Order order=orderService.getOrderById(orderId);
           //order should be returned with an orderId.
           return new ResponseEntity<>(order, HttpStatus.CREATED);
       }catch (OrdernotFoundExceeption ex){
           return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
       }

    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> GetPartnerById(@PathVariable String partnerId){
        try {
            DeliveryPartner deliveryPartner = orderService.getPartnerById(partnerId);

            //deliveryPartner should contain the value given by partnerId

            return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
        }catch (DeliveryPartnerNotFound ex){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> GetOrderCountByPartnerId(@PathVariable String partnerId){

        Integer orderCount = orderService.getOrderCountByPartnerId(partnerId);

        //orderCount should denote the orders given by a partner-id

        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> GetOrdersByPartnerId(@PathVariable String partnerId){
        List<String> orders = orderService.getOrdersByPartnerId(partnerId);

        //orders should contain a list of orders by PartnerId

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> GetAllOrders(){
        List<String> orders = orderService.getAllOrders();

        //Get all orders
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> GetCountOfUnassignedOrders(){
        Integer countOfOrders = orderService.getCountOfUnassignedOrders();

        //Count of orders that have not been assigned to any DeliveryPartner

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> GetOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){

        Integer countOfOrders = orderService.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);

        //countOfOrders that are left after a particular time of a DeliveryPartner

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> GetLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        String time =orderService.getLastDeliveryTimeByPartnerId(partnerId);

        //Return the time when that partnerId will deliver his last delivery order.

        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> DeletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        orderService.deletePartnerById(partnerId);

        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> DeleteOrderById(@PathVariable String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        orderService.deleteOrderById(orderId);


        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
