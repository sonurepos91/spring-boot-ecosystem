package com.microservices.catalogservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "productstocksservice")
public interface CatalogServiceClient {

    @GetMapping(value = "/lifecycle/v1/stocks-inventory/check-stocks/{id}")
    public ResponseEntity<String> getCatalogs(@PathVariable("id") Long id);
}
