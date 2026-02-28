package com.chathura.lapmart.order_service_api.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
}