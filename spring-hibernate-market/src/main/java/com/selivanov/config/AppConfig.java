package com.selivanov.config;


import com.selivanov.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.selivanov")
public class AppConfig {
    @Bean
    public SessionFactory sessionFactory() {
        return new Configuration()
                .configure("hibernate-config.xml")
                .addAnnotatedClass(BuyerCard.class)
                .addAnnotatedClass(Buyer.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Manufacturer.class)
                .buildSessionFactory();
    }

}
