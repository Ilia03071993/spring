package com.selivanov.part2.config;

import com.selivanov.part2.entity.*;
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
                .addAnnotatedClass(Teacher.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(StudentCard.class)
                .addAnnotatedClass(Profession.class)
                .addAnnotatedClass(Group.class)
                .buildSessionFactory();
    }
}
