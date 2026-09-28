package com.quickbite.restaurantservice.dto;

public record PresignResponseDto(
    String uploadUrl,
    String objectKey
){}