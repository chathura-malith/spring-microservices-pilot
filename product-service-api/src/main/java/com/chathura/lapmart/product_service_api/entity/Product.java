package com.chathura.lapmart.product_service_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false, length = 45)
    private String brand;
    @Column( nullable = false, length = 45)
    private String model;
    @Column( nullable = false, length = 45)
    private String processor;
    @Column( nullable = false)
    private double price;
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;
}