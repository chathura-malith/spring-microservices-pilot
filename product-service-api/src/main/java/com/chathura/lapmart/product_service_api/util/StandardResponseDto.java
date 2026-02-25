package com.chathura.lapmart.product_service_api.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponseDto {
    private int code;
    private String message;
    private Object data;
}