package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getRandomProducts() {

        List<Product> allProducts = productRepository.findAll();
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
            productDB.setCity(product.getCity());
            productDB.setCategory(product.getCategory());
            productDB.setName(product.getName());
            productDB.setDetails(product.getDetails());
            productOptional = productRepository.save(productDB);
        }
        return Optional.ofNullable(productOptional);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductByCategory_id(List<Long> category_id) {
        return productRepository.findByCategory_idIn(category_id);
    }
}
