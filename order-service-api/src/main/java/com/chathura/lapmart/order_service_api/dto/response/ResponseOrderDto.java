package com.chathura.lapmart.order_service_api.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String customerEmail;

    private List<ResponseOrderItemDto> items;
}