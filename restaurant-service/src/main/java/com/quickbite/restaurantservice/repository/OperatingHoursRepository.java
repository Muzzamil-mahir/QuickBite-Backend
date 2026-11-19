package com.quickbite.restaurantservice.repository;

import com.quickbite.restaurantservice.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperatingHoursRepository extends JpaRepository<OperatingHours, UUID> {
}
