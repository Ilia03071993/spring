package com.selivanov.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "manufacturer")
    private List<Product> products;

    public Manufacturer(){}
    public Manufacturer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        for (Product product : products){
            product.setManufacturer(this);
        }
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
