package com.microservices.catalogservice.controller;

import com.microservices.catalogservice.CatalogServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CatalogController {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CatalogServiceClient catalogServiceClient;

    private static final String STOCK_SERVICE_URI = "http://localhost:8766/lifecycle/v1/stocks-inventory/check-stocks/";

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getcatalog(@PathVariable("id") Long id){
        ResponseEntity<String> entity = restTemplate.getForEntity(STOCK_SERVICE_URI + id, String.class);
        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/feign/{id}")
    public ResponseEntity<String> getcatalogData(@PathVariable("id") Long id){
        ResponseEntity<String> entity = catalogServiceClient.getCatalogs(id);
        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }
}
