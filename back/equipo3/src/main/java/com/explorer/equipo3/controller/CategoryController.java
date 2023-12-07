package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.ICategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    private static final Logger logger = LogManager.getLogger(CategoryController.class);

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        Optional<Category> categorySearch = categoryService.getCategoryById(id);
        if(categorySearch.isPresent()){
            logger.info("La categoria buscada por ID es valida");
            return ResponseEntity.ok(categorySearch.orElseThrow());
        }
        logger.info("La categoria buscada por ID no es valida");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(category));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Optional<Category> categoryOptional = categoryService.updateCategory(id, category);
        if(categoryOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        Optional categoryOptional = categoryService.getCategoryById(id);
        if(categoryOptional.isPresent()){
            categoryService.deleteCategoryById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
