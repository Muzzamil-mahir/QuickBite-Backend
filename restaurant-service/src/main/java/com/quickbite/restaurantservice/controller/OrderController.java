package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurnats")
public class OrderController {

    //============ RESTAURANT OWNER =============

    @GetMapping("/{id}/orders")
    public Object getRestaurantOrders(
            @PathVariable Long id
    ){
        //TODO: Call OrderService
        return null;
    }

    @PostMapping({"/{id}/orders/{orderId}/confirm"})
    public Object confirmOrder(
            @PathVariable Long id,
            @PathVariable Long orderId
    ){
        //TODO: Call OrderService
        return null;
    }

    @PostMapping("/{id}/orders/{orderId}/reject")
    public Object rejectOrder(
            @PathVariable Long id,
            @PathVariable Long orderId
    ){
        // TODO: Call OrderService
        return null;
    }

    @PostMapping("{id}/orders/{orderId}/ready")
    public Object markOrderReady(
            @PathVariable Long id,
            @PathVariable Long orderId
    ){
        // TODO: Call OrderService
        return null;
    }

}
