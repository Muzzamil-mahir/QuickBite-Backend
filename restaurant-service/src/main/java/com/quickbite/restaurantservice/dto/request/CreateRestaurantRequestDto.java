package com.quickbite.restaurantservice.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateRestaurantRequestDto(

        @NotBlank
        @Size(max = 255)
        String name,

        String description,

        @Pattern(
                regexp = "^(\\+91-?|0)?[6-9][0-9]{9}$",
                message = "Invalid indian mobile number"
        )
        @Size(max = 20)
        String phone,

        @NotBlank
        @Size(max = 100)
        String fssaiNumber,

        @Pattern(
                regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][1-9A-Z]Z[0-9A-Z]$",
                message = "Invalid GSTIN format"
        )
        @Size(max = 20)
        String gstin,

        @NotBlank
        @Size(max = 500)
        String addressLine,

        @NotBlank
        @Size(max = 100)
        String city,

        @NotBlank
        @Size(max = 10)
        String pincode,

        @NotNull
        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitude,

        @NotNull
        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitude,

        @Size(max = 255)
        String bankAccountNumber,

        @Size(max = 20)
        String bankIfsc,

        @Size(max = 255)
        String bankAccountName
) {
}
