package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class MediaController {

    // Restaurant owner
    @PostMapping("/{id}/items/{itemId}/photos/presign")
    public Object presignPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ) {
        return null;
    }

    @PostMapping("/{id}/items/{itemId}/photos/confirm")
    public Object confirmPhoto(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        return null;
    }

    @DeleteMapping("/{id}/items/{itemId}/photos/{photoId}")
    public Object deletePhoto(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @PathVariable Long photoId
    ){
        return null;
    }
}
