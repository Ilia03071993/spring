package com.example.transactions;

import com.example.transactions.entity.Order;
import com.example.transactions.entity.Product;
import com.example.transactions.service.OrderService;
import com.example.transactions.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductService productService, OrderService orderService) {
        return args -> {
            List<Product> products = new ArrayList<>(List.of(
                    new Product(null, "Milk", 10),
                    new Product(null, "Bread", 50),
                    new Product(null, "Honey", 250)
            ));
            Integer productId = 1;
            Integer quantityDecrease = 100;
            Integer quantityIncrease = 10;
            productService.saveProduct(products);

//              productService.decreaseQuantityProduct(productId, quantityDecrease);
//
//
//            executor.shutdown();
//            while (!executor.isTerminated()) {
//
//            }
//
//        };
//
//            ExecutorService executor = Executors.newFixedThreadPool(2);
//            for (int i = 0; i < 1000; i++) {
//                executor.submit(() -> productService.increaseQuantity(productId, quantityIncrease));
//            }
//
//
//            executor.shutdown();
//            while (!executor.isTerminated()) {
//
//            }
        List<Order> orders = new ArrayList<>(List.of(
                new Order(null, "products"),
                new Order(null, "electric device")
        ));

        };

    }
}
