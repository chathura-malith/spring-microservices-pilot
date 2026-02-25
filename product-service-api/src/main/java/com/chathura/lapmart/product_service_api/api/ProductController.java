package com.chathura.lapmart.product_service_api.api;

import com.chathura.lapmart.product_service_api.dto.request.RequestProductDto;
import com.chathura.lapmart.product_service_api.service.ProductService;
import com.chathura.lapmart.product_service_api.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<StandardResponseDto> create(@RequestBody RequestProductDto dto) {
        productService.create(dto);
        return new ResponseEntity<>(
                new StandardResponseDto(201, "Product saved successfully!", null),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDto> update(@PathVariable Long id, @RequestBody RequestProductDto dto) {
        productService.update(id, dto);
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Product updated successfully!", null),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponseDto> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(
                new StandardResponseDto(204, "Product deleted successfully!", null),
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Success!", productService.findById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-all")
    public ResponseEntity<StandardResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String searchText
    ) {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Success!", productService.findAll(page, size, searchText)),
                HttpStatus.OK
        );
    }
}