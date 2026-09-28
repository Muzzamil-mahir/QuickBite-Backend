package com.quickbite.restaurantservice.entity;

import com.quickbite.restaurantservice.enums.RestaurantStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
@DynamicInsert
@DynamicUpdate
public class Restaurants {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID restaurantId;

    @Column(name = "owner_user_id", nullable = false)
    private UUID ownerUserId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "slug", nullable = false, unique = true ,length = 255)
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "phone", length = 20)
    private String phoneNumber;

    @Column(name = "fssai_number", nullable = false, unique = true, length = 100)
    private String fssaiNumber;

    @Column(name = "gstin", length = 20)
    private String gstin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RestaurantStatus status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    @Column(name = "manual_override", nullable = false)
    private boolean manualOverride;

    @Column(name = "commission_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal commissionPct;

    @Column(name = "min_order_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal minOrderValue;

    @Column(name = "avg_prep_minutes", nullable = false)
    private int avgPrepMinutes;

    @Column(name = "avg_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal avgRating;

    @Column(name = "total_ratings", nullable = false)
    private int totalRatings;

    @Column(name = "cuisine_tags", nullable = false)
    private String[] cuisineTags;

    @Column(name = "address_line", nullable = false, length = 500)
    private String addressLine;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "pincode", nullable = false, length = 10)
    private String pincode;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "bank_account_number", length = 255)
    private String bankAccountNumber;

    @Column(name = "bank_ifsc", length = 20)
    private String bankIfsc;

    @Column(name = "bank_account_name", length = 255)
    private String bankAccountName;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;


}

