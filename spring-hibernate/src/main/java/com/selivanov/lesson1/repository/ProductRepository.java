package com.selivanov.lesson1.repository;

//import com.selivanov.hibernate.lesson1.entity.Product;
//import com.selivanov.lesson1.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//public class ProductRepository {
//    private final SessionFactory sessionFactory;
//
//    @Autowired
//    public ProductRepository(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

//    public List<Product> findAllProducts() {
//        Session session = null;
//        //select * from products
//        try {
//            session = sessionFactory.openSession();
//            session.getTransaction().begin();
//
//            List<Product> products = session.createQuery("from Product", Product.class).getResultList();//HQL
//
//            session.getTransaction().commit();
//            return products;
//        } catch (Exception ex) {
//            //4 sql , 3/4 - success
//            if (session != null) {
//                session.getTransaction().rollback();//0/4 - success
//            }
//            throw new RuntimeException(ex);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }

//    public void saveProduct(Product product) {
//        Session session = null;
//        try {
//            session = sessionFactory.openSession();
//            session.getTransaction().begin();
//
//            session.persist(product);
//
//            session.getTransaction().commit();
//        } catch (Exception ex) {
//            if (session != null) {
//                session.getTransaction().rollback();
//            }
//            throw new RuntimeException(ex);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    public void updateProduct(Long id, Product product) {
//        Session session = null;
//        try {
//            session = sessionFactory.openSession();
//            session.getTransaction().begin();
//
//            Product retrievedProduct = session.get(Product.class, id);
//            if (retrievedProduct == null) {
//                throw new RuntimeException("Product is not found");
//            }
//
//            retrievedProduct.setPrice(product.getPrice());
//            retrievedProduct.setName(product.getName());
//            retrievedProduct.setExpirationDate(product.getExpirationDate());
//
//            session.persist(retrievedProduct);
//
//            session.getTransaction().commit();
//        } catch (Exception ex) {
//            if (session != null) {
//                session.getTransaction().rollback();
//            }
//            throw new RuntimeException(ex);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    public void removeProduct(Long id) {
//        Session session = null;
//        try {
//            session = sessionFactory.openSession();
//            session.getTransaction().begin();
//
//            Product retrievedProduct = session.get(Product.class, id);
//            if (retrievedProduct == null) {
//                throw new RuntimeException("Product is not found");
//            }
//            session.remove(retrievedProduct);
//
//            session.getTransaction().commit();
//        } catch (Exception ex) {
//            if (session != null) {
//                session.getTransaction().rollback();
//            }
//            throw new RuntimeException(ex);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//}
