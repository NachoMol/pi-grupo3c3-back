package com.explorer.equipo3.controller;
import com.explorer.equipo3.exception.DuplicatedValueException;
import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.dto.ProductDTO;
import com.explorer.equipo3.service.ICategoryService;
import com.explorer.equipo3.service.IDetailService;
import com.explorer.equipo3.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private MediaController mediaController;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Product>> getPaginatedProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProductsPage(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/paginationramdom")
    public ResponseEntity<Page<Product>> getPaginableRamdom(Pageable pageable){
        Page<Product> products = productService.getRandomProducts(pageable);
        return  new ResponseEntity<>(products, HttpStatus.OK);
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

    @DeleteMapping("/delete/{id}")
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
