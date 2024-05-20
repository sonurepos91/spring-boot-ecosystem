package com.micoroservices.productstockservice.service;

import com.micoroservices.productstockservice.dtos.ProductStockDto;
import com.micoroservices.productstockservice.models.ProductStock;

import java.util.List;

public interface StockService {
    Long saveStock (ProductStockDto productStockDto);

    ProductStock getStocks (Long id);

    List<ProductStock> getAllStocks ();

    Long getCountByVechicleType (String vechicleType);
}
