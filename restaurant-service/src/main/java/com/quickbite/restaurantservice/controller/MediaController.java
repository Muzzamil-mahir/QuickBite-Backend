package com.quickbite.restaurantservice.controller;

import com.quickbite.restaurantservice.dto.PresignRequestDto;
import com.quickbite.restaurantservice.dto.PresignResponseDto;
import com.quickbite.restaurantservice.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService){
        this.mediaService = mediaService;
    }

    // Restaurant owner
    @PostMapping("/photos/presign")
    public ResponseEntity<PresignResponseDto> presignRestaurantPhoto(
            @PathVariable UUID restaurantId,
            @RequestBody PresignRequestDto request
    ) {
        PresignResponseDto obj = mediaService.getRestaurantImgUploadURL(restaurantId, request.mimeType());
        return ResponseEntity.ok(obj);
    }

    @PostMapping("/items/{itemId}/photos/presign")
    public ResponseEntity<PresignResponseDto> presignItemPhoto(
            @PathVariable UUID restaurantId,
            @PathVariable UUID itemId,
            @RequestBody PresignRequestDto request
    ) {
        PresignResponseDto obj = mediaService.getItemImgUploadURL(restaurantId,itemId, request.mimeType());
        return ResponseEntity.ok(obj);
    }


    @PostMapping("/photos/confirm")
    public Object confirmRestaurantPhoto(
            @PathVariable UUID restaurantId
    ){
        return null;
    }

    @PostMapping("/items/{itemId}/photos/confirm")
    public Object confirmItemPhoto(
            @PathVariable UUID restaurantId,
            @PathVariable UUID itemId
    ){
        return null;
    }

    @DeleteMapping("/photos/{photoId}")
    public Object deleteRestaurantPhoto(
            @PathVariable UUID restaurantId,
            @PathVariable UUID photoId
    ){
        return null;
    }

    @DeleteMapping("/items/{itemId}/photos/{photoId}")
    public Object deleteItemPhoto(
            @PathVariable UUID restaurantId,
            @PathVariable UUID itemId,
            @PathVariable UUID photoId
    ){
        return null;
    }
}