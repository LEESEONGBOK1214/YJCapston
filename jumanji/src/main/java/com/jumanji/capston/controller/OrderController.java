package com.jumanji.capston.controller;

import com.jumanji.capston.data.Order;
import com.jumanji.capston.data.OrderMenu;
import com.jumanji.capston.service.OrderMenuServiceImpl;
import com.jumanji.capston.service.OrderServiceImpl;
import com.jumanji.capston.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    OrderMenuServiceImpl orderMenuService;

//    @Transactional(readOnly = true)
//    @GetMapping("/cartId")
//    public ResponseEntity<?> getCartId(){
//        return orderService.getCartId();
//    }

    @Transactional(readOnly = true)
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrder(@RequestHeader String authorization, @PathVariable String orderId){
        Long orderLong = Long.parseLong(orderId);
        Timestamp orderTime = new Timestamp(orderLong);
        List<OrderMenu> orderMenuList = orderMenuService.getList(authorization, orderTime);
//        Order order =orderService.get(authorization,orderTime);
//        Order.Response response = new Order.Response(order);
        List<OrderMenu.Response> response = new ArrayList<>();

        for(OrderMenu orderMenu : orderMenuList){
            response.add(new OrderMenu.Response(orderMenu));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/order") // My Order List
    public ResponseEntity<?> getOrderList(@RequestHeader String authorization){
        List<Order> orderList = orderService.getList(authorization);
        List<Order.Response> response = new ArrayList<>();
        for(Order order : orderList ){
            response.add(new Order.Response(order));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/order")
    public ResponseEntity<?> postOrder(@RequestHeader String authorization, @RequestBody Order.Request request){
//        System.out.println("request info \n" + request.getQuantity() +"\n" + request.getOrderRequest() +"\n" + request.getShopId() +"\n" + request.getUserId());
        Order order = orderService.post(authorization, request);
        Order.Response response = new Order.Response(order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/order")
    public ResponseEntity<?> patchOrder(@RequestHeader String authorization, @RequestBody Order.Request request){
        Order order = orderService.patch(authorization, request);
        Order.Response response = new Order.Response(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/orders")
    public ResponseEntity<?> getOrderAll(){
        return new ResponseEntity<>(orderService.getList(), HttpStatus.OK);
    }

//    @Transactional
//    @DeleteMapping("/order/{orderId}")
//    public ResponseEntity<?> deleteOrder(@RequestHeader String authorization, @PathVariable Timestamp orderId){
//        orderService.delete(authorization, orderId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}