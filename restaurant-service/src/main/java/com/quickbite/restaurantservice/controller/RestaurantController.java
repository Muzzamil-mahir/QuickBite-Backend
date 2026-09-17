package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    // ==================== PUBLIC ====================

    @GetMapping("/search")
    public Object searchRestaurants() {
        // TODO: Call RestaurantService
        return null;

    }

    @GetMapping("/{id}")
    public Object getRestaurant(
            @PathVariable Long id
    ) {
        // TODO: Call RestaurantService
        return null;
    }

    @GetMapping("/search/autocomplete")
    public Object autocompleteRestaurants() {
        // TODO: Call RestaurantService
        return null;
    }

    // ==================== RESTAURANT OWNER ====================

    @GetMapping("/my")
    public Object getMyRestaurant() {
        // TODO: Call RestaurantService
        return null;
    }

    @GetMapping("/{id}/hours")
    public Object getRestaurantHours(
            @PathVariable Long id
    ) {
        // TODO: Call RestaurantService
        return null;
    }

    @GetMapping("/{id}/earnings")
    public Object getRestaurantEarnings(
            @PathVariable Long id
    ) {
        // TODO: Call RestaurantService
        return null;
    }

    @PostMapping
    public Object createRestaurant() {
        // TODO: Call RestaurantService
        return null;
    }

    @PutMapping("/{id}")
    public Object updateRestaurant(
            @PathVariable Long id
    ) {
        // TODO: Call RestaurantService
        return null;
    }

    @PutMapping("/{id}/hours")
    public Object updateRestaurantHours(
            @PathVariable Long id
    ) {
        // TODO: Call RestaurantService
        return null;
    }

    @PatchMapping("/{id}/availability")
    public Object updateAvailability(
            @PathVariable Long id
    ) {
        // TODO: Call RestaurantService
        return null;
    }
}
