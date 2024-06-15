package com.example.springjunit.util;

import java.util.Objects;

public class Client {
    private String phone;
    private String name;

    public Client(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(phone, client.phone) && Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, name);
    }
}