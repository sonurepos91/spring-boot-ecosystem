package com.microservices.productenquiryservice.controller;

import com.microservices.productenquiryservice.dtos.ProductStockDto;
import com.microservices.productenquiryservice.interfaces.ProductStockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class EnquiryServiceController<T> {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ProductStockClient productStockClient;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/enquiry-stock/{id}")
    public ResponseEntity<String> getStockDetailsById (@PathVariable Long id)  {

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8766/lifecycle/v1/stocks-inventory/check-stocks/" + id, String.class);
        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }

    @PostMapping(value = "/save-stock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStocks (@RequestBody ProductStockDto productStockDto) {

        HttpEntity entity = new HttpEntity(productStockDto);
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8766/lifecycle/v1/stocks-inventory/save-stocks", HttpMethod.POST, entity, String.class);
        return new ResponseEntity<>(exchange.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/enquiry-stock/feign-way/{id}")
    public ResponseEntity<T> getStocksDetailsByFeignClient(@PathVariable("id") Long id){
        System.out.println("Product-Enquire-Service : Port :: " + environment.getProperty("local.server.port"));
        ResponseEntity<String> stockDetails = productStockClient.getStockDetails(id);
        return new ResponseEntity<>((T)stockDetails.getBody(), HttpStatus.OK);
    }
}
