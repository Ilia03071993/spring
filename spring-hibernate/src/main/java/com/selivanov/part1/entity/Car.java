package com.selivanov.part1.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String model;
    @Column(nullable = false)
    private String owner;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDate date;
    public  Car (){}
    public Car(String id, String model, String owner, BigDecimal price, LocalDate date) {
        this.id = id;
        this.model = model;
        this.owner = owner;
        this.price = price;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", owner='" + owner + '\'' +
                ", price=" + price +
                ", year=" + date +
                '}';
    }
}