package com.chathura.lapmart.product_service_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductDto {

    @NotBlank(message = "Brand name is required")
    @Size(min = 2, max = 50, message = "Brand name must be between 2 and 50 characters")
    private String brand;

    @NotBlank(message = "Model name is required")
    private String model;

    @NotBlank(message = "Processor details are required")
    private String processor;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;
}