package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.ProductController;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private IProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        Pageable pageable =null;
        Page<Product>products = productRepository.findAll2(pageable);
        logger.info("retornamos todos los products");
        return products.getContent();
    }

    @Override
    public Page<Product> getAllProductsPage(Pageable pageable) {
        return productRepository.findAll2(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getRandomProducts() {
        logger.info("Ingresamos al metodo de products random");
        Pageable pageable = null;
        Page<Product> products = productRepository.findAll2(pageable);
        List<Product> allProducts =products.getContent();
        int totalProducts = allProducts.size();

        if (totalProducts <= 10) {
            logger.info("traemos todos los products");
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
        logger.info("devolvemos los 10 productos random");
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
        logger.info("product encontrado");
        if (productExist.isPresent()){
            Product productDB = productExist.orElseThrow();
            productDB.setCity(product.getCity());
            productDB.setCategory(product.getCategory());
            productDB.setName(product.getName());
            productDB.setDetails(product.getDetails());
            productDB.setState(product.getState());
            productOptional = productRepository.save(productDB);
        }
        return Optional.ofNullable(productOptional);
    }

    @Override
    @Transactional
    public String deleteProductById(Long id) {
        logger.info("buscamos el product");
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
