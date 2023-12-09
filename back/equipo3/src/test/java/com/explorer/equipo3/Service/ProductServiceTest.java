package com.explorer.equipo3.Service;

import com.explorer.equipo3.model.Product;
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

    @Test
    @Order(1)
    void getAllProducts(){
        logger.info("Comenzamos test de getAllProducts");
        List<Product> getAllProductsTest = productService.getAllProducts();
        assertTrue(getAllProductsTest.size() > 1);
    }

    


}
