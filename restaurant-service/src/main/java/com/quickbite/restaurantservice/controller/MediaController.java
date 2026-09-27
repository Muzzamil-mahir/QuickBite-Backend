package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{id}")
public class MediaController {

    // Restaurant owner
    @PostMapping("/photos/presign")
    public Object presignRestaurantPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ) {
        return null;
    }

    @PostMapping("/photos/confirm")
    public Object confirmRestaurantPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        return null;
    }

    @PostMapping("/items/{itemId}/photos/presign")
    public Object presignItemPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ) {
        return null;
    }

    @PostMapping("/items/{itemId}/photos/confirm")
    public Object confirmItemPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        return null;
    }

    @DeleteMapping("/photos/{photoId}")
    public Object deleteRestaurantPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @PathVariable Long photoId
    ){
        return null;
    }

    @DeleteMapping("/items/{itemId}/photos/{photoId}")
    public Object deleteItemPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @PathVariable Long photoId
    ){
        return null;
    }
}
