package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{id}/reviews")
public class ReviewController {
    //============= PUBLIC OWNER =========================
    @GetMapping
    public Object getReviews(
            @PathVariable Long id
    ){
        // TODO:Call ReviewService
        return null;
    }

    //=================== RESTAURANT OWNER =======================

    @PostMapping("/{reviewId}/reply")
    public Object replyToReviews(
            @PathVariable Long id,
            @PathVariable Long reviewId
    ){
        //TODO: Call ReviewService
        return null;
    }
}
