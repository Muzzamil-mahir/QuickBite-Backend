package com.quickbite.restaurantservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "operating_hours",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_restaurant_day",
                        columnNames = {"restaurant_id", "day_of_week"}
                )
        }
)
@DynamicInsert
@DynamicUpdate
public class OperatingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID operatingHoursId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "restaurant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "operating_hours_restaurant_id_fkey")
    )
    private Restaurants restaurant;

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;
}
