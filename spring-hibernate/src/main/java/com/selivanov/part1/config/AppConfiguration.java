package com.selivanov.part1.config;

import com.selivanov.part1.entity.Car;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@ComponentScan("com.selivanov")
public class AppConfiguration {
    /*XML Configuration */
//    @Bean
//    public SessionFactory sessionFactory(){
//        return new org.hibernate.cfg.Configuration()
//                .configure("hibernate-config.xml")
//                .addAnnotatedClass(Car.class)
//                .buildSessionFactory();
//    }

    /*Java Code configuration*/
    @Bean
    public SessionFactory sessionFactory(Configuration configuration){
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Primary
    @Bean
    public Configuration configuration(){
        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/springcore");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "create-drop");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");
        properties.put(Environment.GLOBALLY_QUOTED_IDENTIFIERS, "true");
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Car.class);

        return configuration;
    }

}
