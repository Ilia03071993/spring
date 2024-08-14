package com.example.springdocker.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Integer id;
    private String name;

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


}