package com.microservices.productenquiryservice.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductStockDto {

    private String productName;

    private BigDecimal productPrice;
}
