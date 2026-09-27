package com.quickbite.restaurantservice.dto;

public record PresignResponse (
    String uploadUrl,
    String objectKey
){}