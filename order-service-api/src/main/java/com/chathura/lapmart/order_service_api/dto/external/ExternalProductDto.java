package com.chathura.lapmart.order_service_api.dto.external;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalProductDto {
    private Long id;
    private String brand;
    private String model;
    private String processor;
    private double price;
    private int stockQuantity;
}