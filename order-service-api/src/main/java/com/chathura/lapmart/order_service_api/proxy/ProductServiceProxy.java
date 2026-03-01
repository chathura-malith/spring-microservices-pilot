package com.chathura.lapmart.order_service_api.proxy;

import com.chathura.lapmart.order_service_api.dto.external.ExternalProductDto;
import com.chathura.lapmart.order_service_api.util.StandardResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service-api")
public interface ProductServiceProxy {

    @GetMapping("/api/v1/products/{id}")
    StandardResponseDto getProductById(@PathVariable("id") Long id);

    @PutMapping("/api/v1/products/update-stock/{id}")
    void updateStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}