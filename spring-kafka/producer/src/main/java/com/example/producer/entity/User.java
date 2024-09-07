package com.example.producer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    private LocalDateTime creationDateTime;

    private LocalDateTime expirationDateTime;
}

//@Getter
//@Setter
//@Entity
//@Table(name = "producer")
//public class Producer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String message;
//
//    private Integer user_id;
//
//    private LocalDateTime creationDateTime;
//
//    private LocalDateTime expirationDateTime;
//}