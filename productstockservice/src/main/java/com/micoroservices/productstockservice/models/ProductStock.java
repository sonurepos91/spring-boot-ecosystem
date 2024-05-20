package com.micoroservices.productstockservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name="productStock")
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", nullable = false)
    private Long id;


    @Column(name="product_name", nullable = false)
    private String productName;

    @Column(name="product_price", nullable = false)
    private BigDecimal productPrice;







}
