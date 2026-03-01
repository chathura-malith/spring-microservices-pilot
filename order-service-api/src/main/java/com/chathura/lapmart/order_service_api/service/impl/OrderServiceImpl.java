package com.chathura.lapmart.order_service_api.service.impl;

import com.chathura.lapmart.order_service_api.dto.external.ExternalProductDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestOrderDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestOrderItemDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.paginate.OrderPaginateResponseDto;
import com.chathura.lapmart.order_service_api.entity.Order;
import com.chathura.lapmart.order_service_api.entity.OrderItem;
import com.chathura.lapmart.order_service_api.mapper.OrderMapper;
import com.chathura.lapmart.order_service_api.proxy.ProductServiceProxy;
import com.chathura.lapmart.order_service_api.repo.OrderRepo;
import com.chathura.lapmart.order_service_api.service.OrderService;
import com.chathura.lapmart.order_service_api.util.StandardResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;
    private final ProductServiceProxy productServiceProxy;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void create(RequestOrderDto dto) {
        Order order = orderMapper.toOrder(dto);
        order.setOrderDate(LocalDateTime.now());

        double totalAmount = 0.0;
        List<OrderItem> itemsList = new ArrayList<>();

        for (RequestOrderItemDto itemDto : dto.getItems()) {
            StandardResponseDto response = productServiceProxy.getProductById(itemDto.getProductId());

            if (response == null) {
                throw new RuntimeException("Product not found: " + itemDto.getProductId());
            }

            ExternalProductDto product = objectMapper.convertValue(response.getData(), ExternalProductDto.class);
            productServiceProxy.updateStock(itemDto.getProductId(), itemDto.getQuantity());

            OrderItem orderItem = orderMapper.toOrderItem(itemDto, product);
            orderItem.setOrder(order);

            totalAmount += (product.getPrice() * itemDto.getQuantity());
            itemsList.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(itemsList);

        orderRepo.save(order);
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
