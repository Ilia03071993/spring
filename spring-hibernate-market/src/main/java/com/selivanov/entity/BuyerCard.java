package com.selivanov.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "buyer_cards")
public class BuyerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String email;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "buyerCard")
    private Buyer buyer;

    public BuyerCard() {
    }

    public BuyerCard(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        buyer.setBuyerCard(this);
        this.buyer = buyer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "BuyerCard{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
