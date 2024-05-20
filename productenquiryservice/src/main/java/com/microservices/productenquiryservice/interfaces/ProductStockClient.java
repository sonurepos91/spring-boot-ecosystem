package com.microservices.productenquiryservice.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productstocksservice",url = "http://localhost:8766")
public interface ProductStockClient {


    @RequestMapping(method = RequestMethod.GET, value = "/lifecycle/v1/stocks-inventory/check-stocks/{id}")
    public  ResponseEntity<String> getStockDetails (@PathVariable("id") Long id);

}
