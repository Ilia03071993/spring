package com.example.springdtostock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringDtoStockApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDtoStockApplication.class, args);
    }
}

/**
 * JSON WEB TOKEN
 * <p>
 * secret key: 123 - decode with secret key only,
 * secret key is important in case of hacking attack.
 * <p>
 * 1. Header:12345
 * 2. Payload: hello
 * 3. Signature:
 * 1) encrypt('header' + 'payload' + 'secret key') -> encode(eg1242f) - correct signature
 * 12345  +  'hi'     +  '123'
 * 2) encrypt('header' + 'payload' + 'secret key') -> encode(fsgga134) - incorrect signature
 * 54321  +  'hi'     +  '123'
 **/