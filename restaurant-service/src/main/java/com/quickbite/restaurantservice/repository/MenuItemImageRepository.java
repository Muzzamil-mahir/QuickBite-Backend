package com.quickbite.restaurantservice.repository;

import com.quickbite.restaurantservice.entity.MenuItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemImageRepository extends JpaRepository<MenuItemImage, UUID> {
    long countByItemMenuItemId(UUID itemId);
}
