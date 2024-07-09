package com.example.springjunit.repository;

import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager entityManager;
    private Client client;

    @BeforeEach
    void init() {
        client = Client.builder()
                .name("John Doe")
                .phone("89160976967")
                .build();

        client = entityManager.merge(client);

        // Создаем заказы и связываем их с клиентом
        Order order1 = Order.builder()
                .id(1)
                .name("product")
                .client(client)
                .build();

        Order order2 = Order.builder()
                .id(2)
                .name("chemistry")
                .client(client)
                .build();

        orderRepository.saveAll(List.of(order1, order2));
    }

    @AfterEach
    void destroy() {
        entityManager.clear();
        entityManager
                .createNativeQuery("TRUNCATE TABLE orders RESTART IDENTITY ")
                .executeUpdate();
    }

    @Test
    void getAllOrdersByClientId_shouldReturnOrders_ifClientExist() {
        List<Order> orders = orderRepository.getAllOrdersByClientId(client.getId());
        assertThat(orders.size()).isGreaterThan(0);
    }

    @Test
    void updateOrderByClientPhone() {
        Integer orderId = 1;
        String updateName = "milk";
        Integer orderIndex = 0;
        String phone = client.getPhone();
        orderRepository.updateOrderByClientPhone(phone, updateName, orderId);

        // Синхронизация изменений с базой данных
        entityManager.flush();
        entityManager.clear();

        List<Order> orders = orderRepository.findAll();

        assertThat(orders.get(orderIndex).getName()).isEqualTo(updateName);
    }

    @Test
    void deleteOrderByClientPhone_deletedOrder_ifClientExist() {
        Integer orderId = 1;
        String phone = client.getPhone();

        orderRepository.deleteOrderByClientPhone(phone, orderId);

        // Синхронизация изменений с базой данных
        entityManager.flush();
        entityManager.clear();

        assertThat(orderRepository.findById(orderId).isEmpty()).isTrue();
    }
}