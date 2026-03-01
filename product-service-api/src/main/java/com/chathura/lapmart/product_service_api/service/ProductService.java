package com.chathura.lapmart.product_service_api.service;

import com.chathura.lapmart.product_service_api.dto.request.RequestProductDto;
import com.chathura.lapmart.product_service_api.dto.response.ResponseProductDto;
import com.chathura.lapmart.product_service_api.dto.response.paginate.ProductPaginateResponseDto;

public interface ProductService {
    public void create(RequestProductDto dto);

    public void update(Long id, RequestProductDto dto);

    public void delete(Long id);

    public ResponseProductDto findById(Long id);

    public ProductPaginateResponseDto findAll(int page, int size, String searchText);

    public void updateStock(Long id, int quantity);
}
