package com.example.transactions.service;

import com.example.transactions.entity.Product;
import com.example.transactions.exception.IncorrectAmountStateException;
import com.example.transactions.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final TransactionTemplate transactionTemplate;

    @Transactional
    public void saveProduct(List<Product> products) throws Exception {
        for (Product product : products) {
            if (product.getName() == null) {
                throw new Exception("error");
            }
            product.setName(product.getName().toUpperCase());
            productRepository.save(product);
        }
    }

    @Transactional(noRollbackFor = IncorrectAmountStateException.class, rollbackFor = Exception.class)
    public void decreaseQuantityProduct(Integer productId, Integer quantityDecrease) {
        Product product = productRepository.findById(productId).orElseThrow();
        Integer actualQuantity = product.getQuantity();
        if (quantityDecrease < 0 && quantityDecrease > actualQuantity) {
            throw new IncorrectAmountStateException("The quantity of decrease must be more than zero and less than actual quantity of product");
        }

        product.setQuantity(actualQuantity - quantityDecrease);
        productRepository.save(product);
    }

    @Transactional
    public void increaseQuantityProduct(Integer productId, Integer quantityIncrease) {
        Product product = productRepository.findById(productId).orElseThrow();
        Integer actualQuantity = product.getQuantity();

        if (quantityIncrease > 0) {
            product.setQuantity(actualQuantity + quantityIncrease);
            productRepository.save(product);
        }
    }

    @Transactional
    public void increaseQuantity(Integer productId, Integer quantityIncrease) {
        if (quantityIncrease > 0) {
            productRepository.increaseQuantity(productId, quantityIncrease);
        }
    }

    @Transactional
    public void decreaseQuantity(Integer productId, Integer quantityDecrease) {
        Product product = productRepository.findById(productId).orElseThrow();
        Integer actualQuantity = product.getQuantity();

        if (quantityDecrease > 0 && actualQuantity >= quantityDecrease) {
            product.setQuantity(actualQuantity - quantityDecrease);
            productRepository.save(product);
        }
    }
}

//class ProductServiceProxy extends ProductService {
//    @Override
//    public void saveProduct(List<Product> products) {
//        try {
//            //begin
//            super.saveProduct(products);
//            //commit transaction
//        } catch (RuntimeException e){
//            //rollback transaction
//        }
//    }
//}