package com.chathura.lapmart.product_service_api.dto.response.paginate;

import com.chathura.lapmart.product_service_api.dto.response.ResponseProductDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPaginateResponseDto {
    private List<ResponseProductDto> dataList;
    private long dataCount;
}
