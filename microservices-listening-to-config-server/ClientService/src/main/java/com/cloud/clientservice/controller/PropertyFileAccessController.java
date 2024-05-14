package com.cloud.clientservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping(value="/access")
public class PropertyFileAccessController {


    @Value("${file.name}")
    private String myKey;

    @GetMapping(value="/getProperty",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProperty(){

        return new ResponseEntity<>("applicationName : " + myKey,HttpStatus.OK);
    }

}
