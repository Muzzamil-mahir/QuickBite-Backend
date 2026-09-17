package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{id}/items/{itemId}")
public class MediaController {

    // Restaurant owner
    @PostMapping("/photos/presign")
    public Object presignPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ) {
        return null;
    }

    @PostMapping("/photos/confirm")
    public Object confirmPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        return null;
    }

    @DeleteMapping("/photos/{photoId}")
    public Object deletePhoto(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @PathVariable Long photoId
    ){
        return null;
    }
}
