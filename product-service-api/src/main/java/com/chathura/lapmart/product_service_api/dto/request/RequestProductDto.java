package com.chathura.lapmart.product_service_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductDto {
    private String brand;
    private String model;
    private String processor;
    private double price;
    private int stockQuantity;
}