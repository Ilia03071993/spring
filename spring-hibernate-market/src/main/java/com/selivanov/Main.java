package com.selivanov;

import com.selivanov.config.AppConfig;
import com.selivanov.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);


        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();

//        Buyer buyer = new Buyer(null, "Glen");
//        BuyerCard buyerCard = new BuyerCard(null,"glen@gmail.com");
//
//        buyer.setBuyerCard(buyerCard);
//
//        session.persist(buyer);
//        session.persist(buyerCard);
//
//        Category category = new Category(null, "food");
//        Product product1 = new Product(null, "bread");
//        Product product2 = new Product(null, "milk");
//
//
//        category.setProducts(List.of(product1,product2));
//
//        session.persist(category);
//
//        Order order1 = new Order(null, "Linda");
//        Order order2 = new Order(null, "Fiona");
//        Product product = new Product(null, "tea");
//        product.setOrders(List.of(order1,order2));
//
//        session.persist(product);
//
//
//        Buyer buyer2 = new Buyer(null, "Fred");
//        BuyerCard buyerCard = new BuyerCard(null,"glen@gmail.com");
//
//        buyerCard.setBuyer(buyer2);
//
//        session.persist(buyer2);
//
//        BuyerCard buyerCard1 = session.get(BuyerCard.class, 1);
//     //   Buyer buyer1 = buyerCard1.getBuyer();
//        System.out.println(buyerCard1);
//
//        Category category = new Category(null, "food");
//        Product product1 = new Product(null, "bread");
//        Product product2 = new Product(null, "milk");
//
//
//        category.setProducts(List.of(product1, product2));
//
//        session.persist(category);
//        List<Product> products = category.getProducts();
//        System.out.println(products);
//        Product product = session.get(Product.class, 1);
//        System.out.println(product);
//
//        Order order = new Order(null, "Kit");
//        Product product = new Product(null, "orange");
//        Product product2 = new Product(null, "tea");

        Category category = new Category(null, "food");
        Product sprite = new Product(null, "Sprite");
        Product fanta = new Product(null, "Fanta");
        Manufacturer manufacturer = new Manufacturer(null, "Cola");
        Order order1 = new Order(null, "Olly");
        Order order2 = new Order(null, "Whole Foods");

        Category toys = new Category(null, "toys");
        Product ironMan = new Product(null, "IronMan");
        Manufacturer marvel = new Manufacturer(null, "Marvel");
        Order eldorado = new Order(null , "eldorado");

        Buyer jake = new Buyer(null, "Jake");
        BuyerCard jakeCard = new BuyerCard(null, "jake@gmail.com");
        Buyer linda = new Buyer(null, "Linda");
        BuyerCard lindaCard = new BuyerCard(null, "linda@gmail.com");

        jake.setBuyerCard(jakeCard);
        linda.setBuyerCard(lindaCard);
        linda.setOrders(List.of(order1,order2));
        jake.setOrders(List.of(eldorado));
        session.persist(jake);
        session.persist(linda);

        marvel.setProducts(List.of(ironMan));
        toys.setProducts(List.of(ironMan));
        eldorado.setProducts(List.of(ironMan));

        session.persist(ironMan);

        manufacturer.setProducts(List.of(sprite, fanta));
        category.setProducts(List.of(sprite, fanta));


        order1.setProducts(List.of(sprite));
        order2.setProducts(List.of(sprite, fanta));


        session.persist(sprite);
        session.persist(fanta);

//        session.remove(sprite);
//        session.remove(fanta);

//        Product getProduct = session.get(Product.class, 2);
//        System.out.println(getProduct);

//        Category categoryEntity = session.get(Category.class, 1);
//        System.out.println(categoryEntity);

//        Order order = session.get(Order.class, 2);
//        System.out.println(order);
        //if select in Order @ManyToMany(fetch = FetchType.EAGER) automatic downloads products(left join).


        session.getTransaction().commit();
        context.close();
    }
}