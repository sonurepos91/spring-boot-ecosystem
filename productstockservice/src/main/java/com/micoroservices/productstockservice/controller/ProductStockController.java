package com.micoroservices.productstockservice.controller;

import com.micoroservices.productstockservice.dtos.ProductStockDto;
import com.micoroservices.productstockservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductStockController<T> {

    @Autowired
    private StockService stockService;

    @PostMapping(value = "/save-stocks",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> saveStocks(@RequestBody ProductStockDto productStockDto){
        return new ResponseEntity<>((T)stockService.saveStock(productStockDto), HttpStatus.OK);
    }

    @GetMapping(value = "/check-stocks/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> saveStocks(@PathVariable Long id){

        return new ResponseEntity<>((T)stockService.getStocks(id), HttpStatus.OK);
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getAllStocks(){

        return new ResponseEntity<>((T)stockService.getAllStocks(), HttpStatus.OK);
    }
    @GetMapping(value = "/vechicleType/{vechicleType}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getVechicleCountByVechicleType(@PathVariable("vechicleType") String vechicleType){

        return new ResponseEntity<>((T)stockService.getCountByVechicleType(vechicleType), HttpStatus.OK);
    }

}
