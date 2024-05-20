package com.micoroservices.productstockservice.service;

import com.micoroservices.productstockservice.dtos.ProductStockDto;
import com.micoroservices.productstockservice.models.ProductStock;
import com.micoroservices.productstockservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements  StockService{

    @Autowired
    private StockRepository stockRepository;

    @Override
    @Transactional
    public Long saveStock (ProductStockDto productStockDto) {
        ProductStock productStock = new ProductStock();
        productStock.setProductName(productStockDto.getProductName());
        productStock.setProductPrice(productStockDto.getProductPrice());
        return stockRepository.save(productStock).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductStock getStocks (Long id) {
        Optional<ProductStock> productStock= stockRepository.findById(id);
        if(productStock.isPresent()){
            return productStock.get();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductStock> getAllStocks () {
        return  stockRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountByVechicleType (String vechicleType) {
        return stockRepository.countByProductName(vechicleType);
    }
}
