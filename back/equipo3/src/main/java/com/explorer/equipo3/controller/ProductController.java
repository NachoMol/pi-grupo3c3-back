package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/random")
    public ResponseEntity<List<Product>> getRandomProducts(){return ResponseEntity.ok(productService.getRandomProducts());}

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        Optional<Product> productSearch = productService.getProductById(id);
        if(productSearch.isPresent()){
            return ResponseEntity.ok(productSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product){
        Optional<Product> productOptional = productService.updateProduct(id, product);
        if(productOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Optional productOptional = productService.getProductById(id);
        if(productOptional.isPresent()){
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-categories")
    public ResponseEntity<List<Product>> findProductByCategories(@RequestParam List<Long> categories) {
        List<Product> products = productService.getProductByCategories(categories);
        return ResponseEntity.ok(products);
    }


}
