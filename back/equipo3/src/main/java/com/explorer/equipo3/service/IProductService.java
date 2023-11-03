package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> getAllProducts();

    List<Product> getRandomProducts();
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    Optional<Product> updateProduct(Long id, Product product);
    void deleteProductById(Long id);
}
