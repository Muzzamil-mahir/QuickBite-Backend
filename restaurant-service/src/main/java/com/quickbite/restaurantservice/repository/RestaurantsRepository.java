package com.quickbite.restaurantservice.repository;

import com.quickbite.restaurantservice.entity.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<Restaurants, UUID> {
    boolean existByFssaiNumbar(String fssaiNumber);
    boolean existBySlug(String slug);
}
