package com.selivanov.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private Integer series;

    @OneToOne(mappedBy = "passport",cascade = {CascadeType.PERSIST}) //2.3 CascadeType.REMOVE
    private Person person;

    public Passport() {
    }

    public Passport(Integer id, Integer number, Integer series) {
        this.id = id;
        this.number = number;
        this.series = series;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        person.setPassport(this);
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number=" + number +
                ", series=" + series +
                '}';
    }
}
