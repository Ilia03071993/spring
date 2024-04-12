package com.example.springdtostock.dto;

import com.example.springdtostock.entity.Order;
import com.example.springdtostock.entity.OrderItem;
import com.example.springdtostock.entity.Product;
import com.example.springdtostock.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record CreateOrderRequest(String customerName, List<OrderItemRequest> items) {
    public CreateOrderRequest{
        Objects.requireNonNull(customerName,"Customer name must not be null");
        Objects.requireNonNull(items, "Items must not be null");
    }

    public Order toOrder(ProductService productService) {
        Order order = new Order();
        order.setName(customerName);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : items) {
            Product product = productService.findProduct(orderItemRequest.getProductId());
            if (product != null){
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setAmount(orderItemRequest.getAmount());
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            } else {
                System.out.println("Order don't create");
            }
        }
        order.setOrderItemList(orderItems);
        return order;
    }
}

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CreateOrderRequest {
//    private String customerName;
//    private List<OrderItemRequest> items = new ArrayList<>();
//}
