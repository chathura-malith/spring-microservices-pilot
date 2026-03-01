package com.chathura.lapmart.order_service_api.service;

import com.chathura.lapmart.order_service_api.dto.request.RequestOrderDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestUpdateOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.paginate.OrderPaginateResponseDto;

import java.util.List;

public interface OrderService {

    public void create(RequestOrderDto dto);

    public void update(Long id, RequestUpdateOrderDto dto);

    public void delete(Long id);

    public ResponseOrderDto findById(Long id);

    public OrderPaginateResponseDto findAll(int page, int size, String searchText);


}