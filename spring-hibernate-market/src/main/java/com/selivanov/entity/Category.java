package com.selivanov.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category")
//    @JoinColumn(name = "product", referencedColumnName = "id")
    private List<Product> products;

    public Category() {}
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        for (Product product : products){
            product.setCategory(this);
        }
        this.products = products;
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
