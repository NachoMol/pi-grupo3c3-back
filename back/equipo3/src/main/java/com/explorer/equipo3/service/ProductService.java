package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        Pageable pageable =null;
        Page<Product>products = productRepository.findAll2(pageable);
        return products.getContent();
    }



    @Override
    public Page<Product> getAllProductsPage(Pageable pageable) {
        return productRepository.findAll2(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getRandomProducts() {
        Pageable pageable = null;
        Page<Product> products = productRepository.findAll2(pageable);
        List<Product> allProducts =products.getContent();
        int totalProducts = allProducts.size();

        if (totalProducts <= 10) {
            return allProducts; // Si tienes menos de 10 productos, simplemente devuelve todos.
        }

        List<Product> randomProducts = new ArrayList<>();
        Random random = new Random();

        // Selecciona 10 productos aleatorios
        while (randomProducts.size() < 10) {
            int randomIndex = random.nextInt(totalProducts);
            Product randomProduct = allProducts.get(randomIndex);
            if (!randomProducts.contains(randomProduct)) {
                randomProducts.add(randomProduct);
            }
        }

        return randomProducts;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(Long id, Product product) {
        Optional<Product> productExist = productRepository.findById(id);
        Product productOptional = null;
        if (productExist.isPresent()){
            Product productDB = productExist.orElseThrow();
            productDB.setCity(product.getCity());
            productDB.setCategory(product.getCategory());
            productDB.setName(product.getName());
            productDB.setDetails(product.getDetails());
            productOptional = productRepository.save(productDB);
        }
        return Optional.ofNullable(productOptional);
    }

    @Override
    @Transactional
    public String deleteProductById(Long id) {

        int updatedRows = productRepository.cancelProduct(id);
        if (updatedRows > 0) {
            return "Producto anulado";
        } else {
            return "No se pudo anular el producto";
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductByCategory_id(List<Long> category_id) {
        return productRepository.findByCategory_idIn(category_id);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Page<Product> getProductByCatergoy_idPageable(List<Long> category_id, Pageable pageable) {
        return productRepository.findByCategory_idInPageable(category_id,pageable);
    }

}
