package com.chathura.lapmart.order_service_api.mapper;

import com.chathura.lapmart.order_service_api.dto.external.ExternalProductDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestOrderDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestOrderItemDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderDto;
import com.chathura.lapmart.order_service_api.dto.response.ResponseOrderItemDto;
import com.chathura.lapmart.order_service_api.entity.Order;
import com.chathura.lapmart.order_service_api.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    ResponseOrderDto toResponseOrderDto(Order order);

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "productId", source = "item.productId")
    @Mapping(target = "brand", source = "product.brand")
    @Mapping(target = "model", source = "product.model")
    @Mapping(target = "processor", source = "product.processor")
    @Mapping(target = "quantity", source = "item.quantity")
    @Mapping(target = "unitPrice", source = "item.unitPrice")
    ResponseOrderItemDto
    toResponseOrderItemDto(OrderItem item, ExternalProductDto product);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    Order toOrder(RequestOrderDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "dto.productId")
    @Mapping(target = "quantity", source = "dto.quantity")
    @Mapping(target = "unitPrice", source = "product.price")
    OrderItem toOrderItem(RequestOrderItemDto dto, ExternalProductDto product);
}