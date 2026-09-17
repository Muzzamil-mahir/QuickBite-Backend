package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{id}/orders")
public class OrderController {

    //============ RESTAURANT OWNER =============

    @GetMapping
    public Object getRestaurantOrders(
            @PathVariable Long id
    ){
        //TODO: Call OrderService
        return null;
    }

    @PostMapping({"/{orderId}/confirm"})
    public Object confirmOrder(
            @PathVariable Long id,
            @PathVariable Long orderId
    ){
        //TODO: Call OrderService
        return null;
    }

    @PostMapping("/{orderId}/reject")
    public Object rejectOrder(
            @PathVariable Long id,
            @PathVariable Long orderId
    ){
        // TODO: Call OrderService
        return null;
    }

    @PostMapping("/{orderId}/ready")
    public Object markOrderReady(
            @PathVariable Long id,
            @PathVariable Long orderId
    ){
        // TODO: Call OrderService
        return null;
    }

}
