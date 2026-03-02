package com.chathura.lapmart.order_service_api.api;

import com.chathura.lapmart.order_service_api.dto.request.RequestOrderDto;
import com.chathura.lapmart.order_service_api.dto.request.RequestUpdateOrderDto;
import com.chathura.lapmart.order_service_api.service.OrderService;
import com.chathura.lapmart.order_service_api.util.StandardResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<StandardResponseDto> createOrder(@RequestBody @Valid RequestOrderDto dto) {
        orderService.create(dto);

        return new ResponseEntity<>(
                new StandardResponseDto(201, "Order created successfully!", null),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDto> update(
            @PathVariable Long id, @RequestBody @Valid RequestUpdateOrderDto dto
    ) {
        orderService.update(id, dto);
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Order updated successfully", null),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponseDto> delete(@PathVariable Long id) {
        orderService.delete(id);
        return new ResponseEntity<>(
                new StandardResponseDto(204, "Order deleted and stock updated successfully",null),
                HttpStatus.NO_CONTENT
        );
    }
}