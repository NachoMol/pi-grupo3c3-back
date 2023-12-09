package com.explorer.equipo3.Service;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.City;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.CategoryService;
import com.explorer.equipo3.service.CityService;
import com.explorer.equipo3.service.IProductService;
import com.explorer.equipo3.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CityService cityService;


    Category categoryTest = new Category("Categoria Test","Test", "test");
    City cityTest = new City("Test");
    Product productTest = new Product("Test",1000.0,categoryTest,cityTest);

    @Test
    @Order(1)
    void getAllProducts(){
        logger.info("Comenzamos test de getAllProducts");
        List<Product> getAllProductsTest = productService.getAllProducts();
        assertTrue(getAllProductsTest.size() > 1);
    }

    @Test
    @Order(2)
    void getAllProductsPageable(){
        logger.info("Comenzamos test de getAllProducts pageable");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> getAllProductsPageableTest = productService.getAllProductsPage(pageable);
        assertTrue(getAllProductsPageableTest.getTotalElements() > 1);
    }

    @Test
    @Order(3)
    void getRandomProducts(){
        logger.info("Comenzamos test de getRandomProducts");
        List<Product> getRandomProductsTest = productService.getRandomProducts();
        assertTrue(getRandomProductsTest.size() > 1);
    }

    @Test
    @Order(4)
    void deleteProduct(){

        logger.info("Comenzamos test de delete Product");

        logger.info("Primero creamos un producto");

        // Guardar primero la categoría y la city
        Category savedCategory = categoryService.saveCategory(categoryTest);
        City savedCity = cityService.saveCity(cityTest);

        // Asignar la categoría guardada al producto
        productTest.setCategory(savedCategory);
        productTest.setCity(savedCity);

        Product saveProductTest = productService.saveProduct(productTest);
        assertNotNull(saveProductTest);

        logger.info("Luego Actualizamos el producto");

        productTest.setName("Test1");
        productTest.setPrice(2000.00);

        assertEquals("Test1",productTest.getName());

        logger.info("Eliminamos el producto, la categoria y la city");

        logger.info("Id del product a eliminar: " + productTest.getId());

        Long id = productTest.getId();

        assertEquals("Producto anulado", productService.deleteProductById(id));

    }


}
