package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.models.Category;
import academy.digitallab.store.product.models.Product;
import academy.digitallab.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestParam( name = "category" , required = false) Long categoryId) {


        if (categoryId == null) {

            List<Product> products = productService.listAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);

        } else {

           List<Product> productsFound = productService.findByCategory(Category.builder().id(1L).build());
           if( productsFound.isEmpty() ){
               return ResponseEntity.noContent().build();
           }
               return ResponseEntity.ok(productsFound);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){

        Product productDB = productService.getProduct(id);

        if( productDB == null ){
           return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDB);

    }



    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, BindingResult bindingResult){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }


    @PutMapping("/id")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") Long id){

        product.setId(id);
        Product productDB = productService.getProduct(id);
        if( productDB == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDB);
    }


    @DeleteMapping("/id")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){

        Product productDB = productService.getProduct(id);
        if( productDB == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( productService.deleteProduct(id) );
    }


    @PutMapping("/id/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id,
                                                      @RequestParam( name = "stock") Integer stock){

        Product productDB = productService.getProduct(id);
        if( productDB == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( productService.updateStock(id,stock) );

    }





}
