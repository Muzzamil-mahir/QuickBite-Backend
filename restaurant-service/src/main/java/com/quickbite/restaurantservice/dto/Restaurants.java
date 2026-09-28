package com.quickbite.restaurantservice.dto;

import java.time.OffsetDateTime
import java.util.UUID;

public record Restaurants(
  UUID id,
  UUID owner_user_id,
  String name,
  String slug,
  String description,
  String phone,
  String fssai_number,
  String gstin,
  Status status,
  String rejection_reason,
  boolean is_open,
  boolean manual_override,
  float commission_pct,
  float min_order_value,
  int avg_prep_minutes,
  float avg_rating,
  int total_ratings,
  String[] cuisine_tags,
  String address_line,
  String city,
  String pincode,
  double latitude,
  double longitude,
  String bank_account_number,
  String bank_ifsc,
  String bank_account_name,
  OffsetDateTime created_at,
  OffsetDateTime updated_at,
  UUID created_by,
  UUID updated_by
) {
}
