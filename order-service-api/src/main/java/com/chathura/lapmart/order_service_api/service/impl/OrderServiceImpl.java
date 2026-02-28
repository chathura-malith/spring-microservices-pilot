package com.chathura.lapmart.order_service_api.service.impl;

import com.chathura.lapmart.order_service_api.dto.request.RequestOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.paginate.OrderPaginateResponseDto;
import com.chathura.lapmart.order_service_api.service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public void create(RequestOrderDto dto) {

    }

    @Override
    public void update(Long id, RequestOrderDto dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ResponseOrderDto findById(Long id) {
        return null;
    }

    @Override
    public OrderPaginateResponseDto findAll(int page, int size, String searchText) {
        return null;
    }
}
