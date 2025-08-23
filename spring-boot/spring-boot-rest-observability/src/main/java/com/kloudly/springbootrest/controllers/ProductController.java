package com.kloudly.springbootrest.controllers;

import com.kloudly.springbootrest.dao.Product;
import com.kloudly.springbootrest.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Product>> findAll(){
     List<Product> allProducts = productService.findAll();
     return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id){
		logger.info("Request received for product ID: {}", id);
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        logger.error("Product not found: {}",id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Product> add(@RequestBody Product product){
		logger.debug("Creating new product: {}", product.getName());        
		Product addedProduct = this.productService.add(product);
        logger.info("Product created successfully with ID: {}", addedProduct.getId());
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Product> update(@RequestBody Product product){
		logger.debug("Updating product: {}", product.getName());        
		Optional<Product> existingProduct = this.productService.findById(product.getId());
        Product updatedProduct = product;
        if(existingProduct.isPresent()){
            updatedProduct = this.productService.update(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        logger.error("Product not found: {}",product.getId());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        Optional<Product> existingProduct = this.productService.findById(id);
        if(existingProduct.isPresent()){
            this.productService.delete(existingProduct.get());
            return ResponseEntity.ok().build();
        }
        logger.error("Product not found: {}",id);
        return ResponseEntity.notFound().build();
    }
}
