package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(Long id, Product product) {
        Optional<Product> productExist = productRepository.findById(id);
        Product productOptional = null;
        if (productExist.isPresent()){
            Product productDB = productExist.orElseThrow();
            productDB.setLocation(product.getLocation());
            productOptional = productRepository.save(productDB);
        }
        return Optional.ofNullable(productOptional);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
