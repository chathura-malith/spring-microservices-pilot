package com.chathura.lapmart.product_service_api.service.impl;

import com.chathura.lapmart.product_service_api.dto.request.RequestProductDto;
import com.chathura.lapmart.product_service_api.dto.response.ResponseProductDto;
import com.chathura.lapmart.product_service_api.dto.response.paginate.ProductPaginateResponseDto;
import com.chathura.lapmart.product_service_api.entity.Product;
import com.chathura.lapmart.product_service_api.exception.EntryNotFoundException;
import com.chathura.lapmart.product_service_api.mapper.ProductMapper;
import com.chathura.lapmart.product_service_api.repo.ProductRepo;
import com.chathura.lapmart.product_service_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    @Override
    public void create(RequestProductDto dto) {
        Product product = productMapper.toEntity(dto);
        productRepo.save(product);
    }

    @Override
    public void update(Long id, RequestProductDto dto) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Product not found with id: " + id));

        productMapper.updateEntityFromDto(dto, existingProduct);

        productRepo.save(existingProduct);
    }

    @Override
    public void delete(Long id) {
        if (!productRepo.existsById(id)) {
            throw new EntryNotFoundException("Can't delete. Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    @Override
    public ResponseProductDto findById(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toResponseDto)
                .orElseThrow(() -> new EntryNotFoundException("Product not found with id: " + id));
    }

    @Override
    public ProductPaginateResponseDto findAll(int page, int size, String searchText) {
        Page<Product> productPage = productRepo.searchAllProduct(searchText, PageRequest.of(page, size));

        List<ResponseProductDto> dtoList = productPage.getContent().stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ProductPaginateResponseDto(dtoList, productPage.getTotalElements());
    }
}
