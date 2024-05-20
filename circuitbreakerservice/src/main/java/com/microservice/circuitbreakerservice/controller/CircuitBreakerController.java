package com.microservice.circuitbreakerservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController<T> {

    Logger log = LogManager.getLogger(CircuitBreakerController.class);

    private int count = 0;

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "productstocksservice", fallbackMethod = "fallbackRandomActivity")
    @GetMapping(value = "/count/vechicleType/{vechicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getUserCountByCarOwnerType (@PathVariable String vechicleType) {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8766/lifecycle/v1/stocks-inventory/vechicleType/" + vechicleType, String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.OK);
    }

    @Retry(name = "productstockservice", fallbackMethod = "fallBackRetryApi")
    @GetMapping(value = "/count/vechicleType/test-retry/{vechicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getUserCountByCarOwnere (@PathVariable String vechicleType) {
        log.info("Retry Count " + count++);
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8766/lifecycle/v1/stocks-inventory/vechicleType/" + vechicleType, String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.OK);
    }

    @Bulkhead(name = "productstockservice", fallbackMethod = "fallBackDueToConcurrentUsers")
    @GetMapping(value = "/count/vechicleType/test-concurrency/{vechicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getUserCountByCarByConcurrenct (@PathVariable String vechicleType) {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8766/lifecycle/v1/stocks-inventory/vechicleType/" + vechicleType, String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.OK);
    }

    @RateLimiter(name = "productstockservice", fallbackMethod = "fallBackDueToRateLimiter")
    @GetMapping(value = "/count/vechicleType/test-rate-limiter/{vechicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getUserCountByCarByCfallBackDueToRateLimiter (@PathVariable String vechicleType) {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8766/lifecycle/v1/stocks-inventory/vechicleType/" + vechicleType, String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/count/gateway/vechicleType/{vechicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getUserCountByForGatewayCarOwnerType (@RequestParam String user, @PathVariable String vechicleType) {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8766/lifecycle/v1/stocks-inventory/vechicleType/" + vechicleType, String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.OK);
    }


    public ResponseEntity<T> fallbackRandomActivity (String vechicleType, Exception ex) {
        log.info("ErrorStackTrace Instances API: " + ex.getMessage());
        return new ResponseEntity<>((T) ex.getMessage(), HttpStatus.TEMPORARY_REDIRECT);
    }

    public ResponseEntity<T> fallBackDueToConcurrentUsers (String vechicleType, Exception ex) {
        log.info("ErrorStackTrace  BulkHead API : " + ex.getMessage());
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8767/lifecycle/v1/stocks-inventory/", String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.TEMPORARY_REDIRECT);
    }

    public ResponseEntity<T> fallBackDueToRateLimiter (String vechicleType, Exception ex) {
        log.info("ErrorStackTrace  RateLimiter API : " + ex.getMessage());
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8767/lifecycle/v1/stocks-inventory/", String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.TEMPORARY_REDIRECT);
    }

    public ResponseEntity<T> fallBackRetryApi (String vechicleType, Exception ex) {
        log.info("ErrorStackTrace  Retry API : " + ex.getMessage());

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8767/lifecycle/v1/stocks-inventory/", String.class);
        return new ResponseEntity<>((T) entity.getBody(), HttpStatus.TEMPORARY_REDIRECT);
    }
}
