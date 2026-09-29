package com.quickbite.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<RestaurantsRepository, UUID> {
}
