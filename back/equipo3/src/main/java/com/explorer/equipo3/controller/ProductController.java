package com.explorer.equipo3.controller;
import com.explorer.equipo3.exception.DuplicatedValueException;
import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.explorer.equipo3.service.ICategoryService;
import com.explorer.equipo3.service.IDetailService;
import com.explorer.equipo3.service.IImageService;
import com.explorer.equipo3.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IDetailService detailService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IImageService imageService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Product>> getPaginatedProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProductsPage(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
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

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam("files") List<MultipartFile> files,
                                 @RequestParam("product") String productJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            List<String> imageUploadResults = Collections.singletonList(imageService.uploadImages(files, "Image data"));

            if (imageUploadResults.contains("Error uploading image.")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            // Asocia las imágenes al producto
            List<Image> images = new ArrayList<>();
            for (MultipartFile multipartFile : files) {
                Image image = new Image();

                try {
                    // Obtén los datos binarios de la imagen en forma de byte array
                    byte[] imageData = multipartFile.getBytes();

                    // Asigna los datos binarios al atributo 'data'
                    image.setData(imageData);

                    // Obtén el nombre del archivo y asígnalo al atributo 'filename'
                    String filename = multipartFile.getOriginalFilename();
                    image.setFilename(filename);

                    String title = filename.substring(0, filename.lastIndexOf('.'));
                    image.setTitle(title);

                    String imageUrl = "http://localhost:8080/images/" + filename;
                    image.setUrl(imageUrl);

                    image.setProduct(product);

                    images.add(image);
                } catch (IOException e) {
                    e.printStackTrace(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
                product.setImages(images);

                // Guarda el producto con las imágenes asociadas
                productService.saveProduct(product);

                return ResponseEntity.status(HttpStatus.CREATED).body(product);
            } catch(JsonProcessingException e){
                e.printStackTrace(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error parsing JSON");
            }catch(DuplicatedValueException e){
                e.printStackTrace(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicated value");
            } catch(Exception e){
                e.printStackTrace(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

    }


    @PostMapping("/create")
    public ResponseEntity<?> addProduct(@RequestBody Product product) throws DuplicatedValueException{
        try {
            // Obtén el ID de categoría desde la solicitud
            Long categoryId = product.getCategory().getId();
            Optional<Product> productOptional = productService.getProductByName(product.getName());

            if(productOptional.isEmpty()) {
                // A continuación, debes buscar la categoría por su ID y configurarla en el producto
                Category category = categoryService.getCategoryById(categoryId).orElse(null);

                if (category != null)  {
                    product.setCategory(category);
                    product.setImages(product.getImages());
                    // Ahora puedes guardar el producto
                    productService.saveProduct(product);
                    return ResponseEntity.status(HttpStatus.CREATED).body(product); // Devuelve el producto creado en la respuesta
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                throw new DuplicatedValueException("Name exist in Database");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        Optional<Product> productOptional = productService.getProductById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();

            // Actualiza solo los campos necesarios
            product.setCategory(updatedProduct.getCategory());
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            // Actualiza otros campos si es necesario

            // Conserva las especificaciones (details) existentes
            Set<Detail> existingDetails = product.getDetails();
            if (existingDetails != null && !existingDetails.isEmpty()) {
                updatedProduct.getDetails().addAll(existingDetails);
            }

            // Asigna los detalles actualizados al producto
            product.setDetails(updatedProduct.getDetails());

            Product savedProduct = productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Optional productOptional = productService.getProductById(id);
        if(productOptional.isPresent()){
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/add-details")
    public ResponseEntity<Product> addDetailsToProduct(
            @PathVariable Long id,
            @RequestBody Set<Long> detailIds) {
        System.out.println("Received detailIds: " + detailIds);
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Aquí debes convertir los IDs en objetos Detail y agregarlos al producto
            for (Long detailId : detailIds) {
                Optional<Detail> detailOptional = detailService.getDetailById(detailId);
                if (detailOptional.isPresent()) {
                    Detail detail = detailOptional.get();
                    product.getDetails().add(detail);
                }
            }

            Product updatedProduct = productService.saveProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byCategories")
    public ResponseEntity<List<Product>> getProductByCategoryIds(@RequestParam List<Long> category_id) {
        try{
            List<Product> products = productService.getProductByCategory_id(category_id);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/byCategoriesPageable")
    public ResponseEntity<Page<Product>> getProductByCatergoy_idPageable(@RequestParam List<Long> category_id,@PageableDefault(size = 10, page = 0)Pageable pageable) {
        try{
            Page<Product> products = productService.getProductByCatergoy_idPageable(category_id,pageable);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
