package com.quickbite.restaurantservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "restaurant_reviews")
@DynamicInsert
@DynamicUpdate
public class RestaurantReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID restaurantReviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "restaurant_id",
            nullable = false
    )
    private Restaurants restaurant;

    @Column(name = "order_id", nullable = false, unique = true)
    private UUID orderId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "owner_reply")
    private String ownerReply;

    @Column(name = "is_flagged", nullable = false)
    private boolean isFlagged;

    @Column(name = "flag_reason", length = 255)
    private String flagReason;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "replied_at")
    private OffsetDateTime repliedAt;
}
