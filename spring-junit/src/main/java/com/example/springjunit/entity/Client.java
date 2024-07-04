package com.example.springjunit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "client",cascade = CascadeType.PERSIST)
    private List<Order> orders;

    public void setOrders(List<Order> orders) {
        for (Order order : orders) {
            order.setClient(this);
        }

        this.orders = orders;
    }
}
