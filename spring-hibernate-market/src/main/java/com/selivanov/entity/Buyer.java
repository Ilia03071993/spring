package com.selivanov.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "buyers")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "buyer_card_id", referencedColumnName = "id")
    private BuyerCard buyerCard;
    public Buyer() {}
    public Buyer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public BuyerCard getBuyerCard() {
        return buyerCard;
    }

    public void setBuyerCard(BuyerCard buyerCard) {
        this.buyerCard = buyerCard;
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

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", buyerCard=" + buyerCard +
                '}';
    }
}
