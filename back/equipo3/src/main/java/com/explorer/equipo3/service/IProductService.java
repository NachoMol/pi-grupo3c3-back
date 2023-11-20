package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> getAllProducts();
    Page<Product> getAllProductsPage(Pageable pageable);
    List<Product> getRandomProducts();
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    Optional<Product> updateProduct(Long id, Product product);
    void deleteProductById(Long id);
    List<Product> getProductByCategory_id(List<Long> category_id);
    Optional<Product> getProductByName(String name);
    Page<Product> getProductByCatergoy_idPageable(List<Long> category_id, Pageable pageable);
    Page<Product> getRandomProducts(Pageable pageable);

}
