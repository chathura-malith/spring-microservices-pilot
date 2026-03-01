package com.chathura.lapmart.order_service_api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateOrderDto {

    @NotEmpty(message = "Order must have at least one item")
    private List<RequestOrderItemDto> items;
}