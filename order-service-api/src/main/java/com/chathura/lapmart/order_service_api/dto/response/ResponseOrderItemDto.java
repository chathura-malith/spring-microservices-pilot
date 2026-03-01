package com.chathura.lapmart.order_service_api.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderItemDto {
    private Long id;
    private Long productId;

    private String brand;
    private String model;
    private String processor;

    private Integer quantity;
    private Double unitPrice;
}