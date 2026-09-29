package com.quickbite.restaurantservice.repository;

import com.quickbite.restaurantservice.entity.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, UUID> {
    long countByItemRestaurantId(UUID restaurantId);
}
