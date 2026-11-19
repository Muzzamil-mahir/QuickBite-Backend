package com.quickbite.restaurantservice.service;

import com.quickbite.restaurantservice.dto.request.CreateRestaurantRequestDto;
import com.quickbite.restaurantservice.dto.response.CreateRestaurantResponseDto;
import com.quickbite.restaurantservice.entity.OperatingHours;
import com.quickbite.restaurantservice.entity.Restaurants;
import com.quickbite.restaurantservice.exception.restaurantservice.DuplicateFssaiExecptiont;
import com.quickbite.restaurantservice.repository.OperatingHoursRepository;
import com.quickbite.restaurantservice.repository.RestaurantsRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Locale;
import java.util.UUID;

@Service
public class RestaurantService {
    public RestaurantService(RestaurantsRepository restaurantsRepository, OperatingHoursRepository operatingHoursRepository) {
        this.restaurantsRepository = restaurantsRepository;
        this.operatingHoursRepository = operatingHoursRepository;
    }

    private final RestaurantsRepository restaurantsRepository;
    private final OperatingHoursRepository operatingHoursRepository;


    @Transactional
    public CreateRestaurantResponseDto createRestaurant(CreateRestaurantRequestDto payload){
        UUID ownerId = UUID.randomUUID();
        String fssaiNumber = payload.fssaiNumber();
        String name = payload.name();

        if(restaurantsRepository.existByFssaiNumbar(fssaiNumber)){
            throw new DuplicateFssaiExecptiont("FSSAI number already registered");
        }

        String slug = generateUniqueSlug(name);
        Restaurants restaurant = new Restaurants(
                ownerId,
                name,
                slug,
                payload.description(),
                payload.phone(),
                fssaiNumber,
                payload.gstin(),
                payload.addressLine(),
                payload.city(),
                payload.pincode(),
                payload.latitude(),
                payload.longitude(),
                payload.bankAccountNumber(),
                payload.bankIfsc(),
                payload.bankAccountName()

        );

        restaurantsRepository.save(restaurant);

        for(int i = 0; i < 1; i++){
            OperatingHours operatingHours = new OperatingHours(
                    restaurant,
                    i,
                    LocalTime.of(10,00),
                    LocalTime.of(10,00),
                    true

            );
            operatingHoursRepository.save(operatingHours);
        }
        return new CreateRestaurantResponseDto("success");
    }

    public String generateUniqueSlug(String restaurantName){
        String baseSlug = restaurantName.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
        String slug = baseSlug;
        if(restaurantsRepository.existBySlug(slug)){
            slug = baseSlug + "-" + UUID.randomUUID().toString().substring(0,6);
        }

        return slug;
    }
}
