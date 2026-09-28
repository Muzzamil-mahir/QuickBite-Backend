package com.quickbite.restaurantservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "item_customisation_options")
@DynamicInsert
@DynamicUpdate
public class ItemCustomisationOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID itemCustomisationOptionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "group_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "item_customisation_options_group_id_fkey"
            )
    )
    private ItemCustomisationGroup group;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(
            name = "additional_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal additionalPrice;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;
}
