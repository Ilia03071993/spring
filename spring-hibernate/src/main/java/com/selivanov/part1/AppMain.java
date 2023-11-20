package com.selivanov.part1;

import com.selivanov.part1.config.AppConfiguration;
import com.selivanov.part1.entity.Car;
import com.selivanov.part1.repository.CarRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AppMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        CarRepository context = applicationContext.getBean(CarRepository.class);

        BigDecimal price = new BigDecimal(3000);
        Car car = new Car();
        car.setDate(LocalDate.of(1999,7,3));
        car.setModel("Jaguar");
        car.setOwner("Fred");
        car.setPrice(price);
    //    context.updateCar(car,1);
       // List<Car> carByYear = context.findCarByYear(LocalDate.of(2020, 9, 9));

         List<Car> cars = context.findAllCars();
        System.out.println(cars);

        applicationContext.close();
    }
}
