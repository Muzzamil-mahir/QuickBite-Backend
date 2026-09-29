package com.quickbite.restaurantservice.entity;

import com.quickbite.restaurantservice.enums.ImageType;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "restaurant_images")
@DynamicInsert
@DynamicUpdate
public class RestaurantImage {

    public RestaurantImage(Restaurants restaurant, String imageUrl, String objectKey, ImageType imageType, int displayOrder, OffsetDateTime createdAt) {
        this.restaurant = restaurant;
        this.imageUrl = imageUrl;
        this.objectKey = objectKey;
        this.imageType = imageType;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID restaurantImageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "restaurant_id",
            nullable = false
    )
    private Restaurants restaurant;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "object_key", nullable = false, length = 300)
    private String objectKey;



    @Enumerated(EnumType.STRING)
    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
