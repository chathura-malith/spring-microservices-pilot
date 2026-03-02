package com.chathura.lapmart.order_service_api.service.impl;

import com.chathura.lapmart.order_service_api.dto.external.ExternalProductDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestOrderDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestOrderItemDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestUpdateOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderItemDto;
import com.chathura.lapmart.order_service_api.dto.response.paginate.OrderPaginateResponseDto;
import com.chathura.lapmart.order_service_api.entity.Order;
import com.chathura.lapmart.order_service_api.entity.OrderItem;
import com.chathura.lapmart.order_service_api.enums.OrderStatus;
import com.chathura.lapmart.order_service_api.exception.ActionNotAllowedException;
import com.chathura.lapmart.order_service_api.exception.EntryNotFoundException;
import com.chathura.lapmart.order_service_api.mapper.OrderMapper;
import com.chathura.lapmart.order_service_api.proxy.ProductServiceProxy;
import com.chathura.lapmart.order_service_api.repo.OrderRepo;
import com.chathura.lapmart.order_service_api.service.OrderService;
import com.chathura.lapmart.order_service_api.util.StandardResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                throw new EntryNotFoundException("Product not found: " + itemDto.getProductId());
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
    @Transactional
    public void update(Long id, RequestUpdateOrderDto dto) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Order not found"));
        if (existingOrder.getOrderStatus() != OrderStatus.PENDING) {
            throw new ActionNotAllowedException(
                    "Order status is " + existingOrder.getOrderStatus() + ". Action not allowed!");
        }

        for (OrderItem existingItem : existingOrder.getItems()) {
            for (RequestOrderItemDto newItemDto : dto.getItems()) {

                if (existingItem.getProductId().equals(newItemDto.getProductId())) {

                    int oldQty = existingItem.getQuantity();
                    int newQty = newItemDto.getQuantity();
                    int difference = newQty - oldQty;

                    if (difference != 0) {
                        productServiceProxy.updateStock(existingItem.getProductId(),difference);

                        existingItem.setQuantity(newQty);
                    }
                }
            }
        }
        double finalTotal = existingOrder.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        existingOrder.setTotalAmount(finalTotal);
        orderRepo.save(existingOrder);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ActionNotAllowedException(
                    "Order status is " + order.getOrderStatus() + ". Action not allowed!");
        }

        for (OrderItem item : order.getItems()) {
            productServiceProxy.updateStock(item.getProductId(), -item.getQuantity());
        }
        orderRepo.delete(order);
    }

    @Override
    public ResponseOrderDto findById(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Order not found with ID: " + id));

        ResponseOrderDto response = orderMapper.toResponseOrderDto(order);

        List<ResponseOrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : order.getItems()) {

            StandardResponseDto proxyResponse = productServiceProxy.getProductById(item.getProductId());
            ExternalProductDto product = objectMapper.convertValue(
                    proxyResponse.getData(), ExternalProductDto.class
            );

            itemDtos.add(orderMapper.toResponseOrderItemDto(item, product));
        }
        response.setItems(itemDtos);
        return response;
    }

    @Override
    public OrderPaginateResponseDto findAll(int page, int size, String searchText) {
        Page<Order> orders = orderRepo.findAllWithSearch(searchText, PageRequest.of(page, size));
        List<ResponseOrderDto> responseList = new ArrayList<>();

        for (Order order : orders.getContent()) {
            ResponseOrderDto orderDto = orderMapper.toResponseOrderDto(order);
            List<ResponseOrderItemDto> itemDtos = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                StandardResponseDto proxyResponse = productServiceProxy.getProductById(item.getProductId());

                ExternalProductDto product = objectMapper.convertValue(
                        proxyResponse.getData(), ExternalProductDto.class
                );

                itemDtos.add(orderMapper.toResponseOrderItemDto(item, product));
            }

            orderDto.setItems(itemDtos);
            responseList.add(orderDto);
        }

        return new OrderPaginateResponseDto(
                orders.getTotalElements(),
                responseList
        );
    }

}
