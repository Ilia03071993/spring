package com.selivanov.part1.config;

import com.selivanov.part1.entity.Car;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarMapper {


    public static void updateModel(Car car, String model){
        car.setModel(model);
    }

    public static void updateOwner(Car car, String owner){
        car.setOwner(owner);
    }

    public static void updatePrice(Car car, BigDecimal price){
        car.setPrice(price);
    }

    public static void updateDate(Car car, LocalDate date){
        car.setDate(date);
    }
}
