package com.quickbite.restaurantservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "menu_item_images")
@DynamicInsert
@DynamicUpdate
public class MenuItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID menuItemImageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "item_id",
            nullable = false
    )
    private MenuItem item;

    @Column(name = "object_key", nullable = false, length = 300)
    private String objectKey;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public MenuItemImage( MenuItem item, String objectKey, String imageUrl, int displayOrder, OffsetDateTime createdAt) {
        this.item = item;
        this.objectKey = objectKey;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
    }
}
