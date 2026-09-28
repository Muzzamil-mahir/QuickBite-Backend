package com.quickbite.restaurantservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "item_customisation_groups")
@DynamicInsert
@DynamicUpdate
public class ItemCustomisationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID itemCustomisationGroupId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "item_customisation_groups_item_id_fkey")
    )
    private MenuItem item;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired;

    @Column(name = "min_selections", nullable = false)
    private int minSelections;

    @Column(name = "max_selections", nullable = false)
    private int maxSelections;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;
}
