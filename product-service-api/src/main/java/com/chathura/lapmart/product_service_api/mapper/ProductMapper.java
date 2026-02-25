package com.chathura.lapmart.product_service_api.mapper;

import com.chathura.lapmart.product_service_api.dto.request.RequestProductDto;
import com.chathura.lapmart.product_service_api.dto.response.ResponseProductDto;
import com.chathura.lapmart.product_service_api.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(RequestProductDto dto);

    ResponseProductDto toResponseDto(Product product);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(RequestProductDto dto, @MappingTarget Product product);
}