package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
    // ==================== ADMIN ====================

    @GetMapping
    public Object getAllRestaurants() {
        // TODO: Call AdminRestaurantService
        return null;
    }

    @GetMapping("/{id}")
    public Object getRestaurant(
            @PathVariable Long id
    ) {
        // TODO: Call AdminRestaurantService
        return null;
    }

    @PostMapping("/reindex")
    public Object reindexRestaurants() {
        // TODO: Call AdminRestaurantService
        return null;
    }

    @PutMapping("/{id}/status")
    public Object updateRestaurantStatus(
            @PathVariable Long id
    ) {
        // TODO: Call AdminRestaurantService
        return null;
    }

    @PutMapping("/{id}/commission")
    public Object updateRestaurantCommission(
            @PathVariable Long id
    ) {
        // TODO: Call AdminRestaurantService
        return null;
    }

    @DeleteMapping("/{id}/reviews/{reviewId}/flag")
    public Object flagReview(
            @PathVariable Long id,
            @PathVariable Long reviewId
    ) {
        // TODO: Call AdminRestaurantService
        return null;
    }
}
