package com.chathura.lapmart.order_service_api.dto.response.paginate;

import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaginateResponseDto {
    private long dataCount;
    private List<ResponseOrderDto> dataList;
}
