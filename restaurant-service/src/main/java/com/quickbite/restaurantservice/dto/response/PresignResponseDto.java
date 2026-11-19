package com.quickbite.restaurantservice.dto.response;

public record PresignResponseDto(
    String uploadUrl,
    String objectKey
){}