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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

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
        logger.info("ingresamos al metodo de traer todos los products");
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
        logger.info("ingresamos al metodo de retornar product por id");
        Optional<Product> productSearch = productService.getProductById(id);
        if(productSearch.isPresent()){
            logger.info("producto encontrado");
            return ResponseEntity.ok(productSearch.orElseThrow());
        }
        logger.info("producto no encontrado");
        return ResponseEntity.notFound().build();


    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<?> getProductImages(@PathVariable Long productId) {
        logger.info("ingresamos al metodo de retornar imagenes del product");
        try {
            Optional<Product> optionalProduct = productService.getProductById(productId);

            if (optionalProduct.isPresent()) {
                logger.info("product encontrado");
                Product product = optionalProduct.get();

                // Obtener las imágenes relacionadas con el producto desde el servicio de imágenes
                List<Image> productImages = imageService.getImagesByProduct(product);

                // Aquí puedes devolver las imágenes o simplemente las URL de las imágenes, según tus necesidades
                List<String> imageUrls = productImages.stream()
                        .map(Image::getUrl)
                        .distinct()
                        .collect(Collectors.toList());
                System.out.println("Image URLs: " + imageUrls);
                logger.info("images encontradas");
                return ResponseEntity.ok(imageUrls);
            } else {
                logger.info("images no encontradas");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam("files") List<MultipartFile> files,
                                 @RequestParam("product") String productJson) {
        logger.info("ingresamos al metodo de agregar images a product");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            String imageUploadResult = imageService.uploadImages(files);

            List<String> imageUploadResults = Collections.singletonList(imageService.uploadImages(files));

            if (imageUploadResults.contains("Error uploading image.")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            // Asocia las imágenes al producto
            List<Image> images = new ArrayList<>();
            for (MultipartFile multipartFile : files) {
                Image image = new Image();

                try {

                    // Obtén el nombre del archivo y asígnalo al atributo 'filename'
                    String filename = multipartFile.getOriginalFilename();
                    image.setFilename(filename);

                    String imageUrl = "https://s3.amazonaws.com/bucket-explorer-images/" + filename;
                    image.setUrl(imageUrl);

                    image.setProduct(product);

                    images.add(image);

                } catch (S3Exception e) {
                    e.printStackTrace(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
                product.setImages(images);

                // Guarda el producto con las imágenes asociadas
                productService.saveProduct(product);

                return ResponseEntity.status(HttpStatus.CREATED).body(product);
            } catch(JsonProcessingException e){
                e.getMessage(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error parsing JSON");
            }catch(DuplicatedValueException e){
                e.getMessage(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicated value");
            } catch(Exception e){
                e.printStackTrace(); // Manejo adecuado de la excepción, puedes personalizar según tus necesidades
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

    }


    @PostMapping("/create")
    public ResponseEntity<?> addProduct(@RequestBody Product product) throws DuplicatedValueException{
        logger.info("ingresamos al metodo de añadir product");
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



    /*@PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        logger.info("ingresamos al metodo de update product por id");
        Optional<Product> productOptional = productService.getProductById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();

            // Actualiza solo los campos necesarios
            product.setCategory(updatedProduct.getCategory());
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCity(updatedProduct.getCity());

            // Conserva las especificaciones (details) existentes
            Set<Detail> existingDetails = product.getDetails();


            // Asigna los detalles actualizados al producto
            Set<Detail> updatedDetails = updatedProduct.getDetails();
            if (updatedDetails != null) {
                // Conserva los detalles existentes solo si hay detalles actualizados
                if (existingDetails != null && !existingDetails.isEmpty()) {
                    updatedDetails.addAll(existingDetails);
                }
                product.setDetails(updatedDetails);
            }

            Product savedProduct = productService.saveProduct(product);
            logger.info("product actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }
        logger.info("product no encontrado");
        return ResponseEntity.notFound().build();
    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        logger.info("Ingresamos al método de actualización de producto por ID");

        Optional<Product> productOptional = productService.getProductById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Actualiza solo los campos proporcionados en la solicitud
            if (updatedProduct.getCategory() != null) {
                product.setCategory(updatedProduct.getCategory());
            }

            if (updatedProduct.getName() != null) {
                product.setName(updatedProduct.getName());
            }

            if (updatedProduct.getPrice() != null) {
                product.setPrice(updatedProduct.getPrice());
            }

            if (updatedProduct.getCity() != null) {
                product.setCity(updatedProduct.getCity());
            }

            // Conserva las especificaciones (details) existentes
            Set<Detail> existingDetails = product.getDetails();

            // Actualiza los detalles solo si se proporcionan en la solicitud
            if (updatedProduct.getDetails() != null) {
                Set<Detail> updatedDetails = updatedProduct.getDetails();
                if (existingDetails != null && !existingDetails.isEmpty()) {
                    updatedDetails.addAll(existingDetails);
                }
                product.setDetails(updatedDetails);
            }

            Product savedProduct = productService.saveProduct(product);
            logger.info("Producto actualizado");
            return ResponseEntity.status(HttpStatus.OK).body(savedProduct);
        }

        logger.info("Producto no encontrado");
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        logger.info("ingresamos al metodo de eliminar product");
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
        logger.info("ingresamos al metodo de añadir details a product");
        logger.info("Received detailIds: " + detailIds);
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            logger.info("product encontrado");
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
            logger.info("details del product actualizado");
            return ResponseEntity.ok(updatedProduct);
        } else {
            logger.info("product no encontrado");
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
