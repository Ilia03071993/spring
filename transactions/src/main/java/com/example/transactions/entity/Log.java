package com.example.transactions.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String operation;
    private String message;
    private LocalDateTime datetime;

    public Log(String operation, String message) {
        this.operation = operation;
        this.message = message;
        this.datetime = LocalDateTime.now();
    }
}
