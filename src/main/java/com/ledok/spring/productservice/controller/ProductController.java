package com.ledok.spring.productservice.controller;

import com.ledok.spring.productservice.controller.dto.ProductDto;
import com.ledok.spring.productservice.controller.dto.ProductFilter;
import com.ledok.spring.productservice.jpa.entity.ProductEntity;
import com.ledok.spring.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity <ProductEntity> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @ModelAttribute ProductFilter filter,
            Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/api/products/batch")
    List<ProductDto> getProductsByIds(@RequestBody List<Long> ids){
        List <ProductDto> productDtos = productService.getProductsByIds(ids);
        return productDtos;
    }

    @PostMapping("/check-availability")
    boolean checkProductsAvailability(@RequestBody Map<Long, Integer> productQuantities){
        return productService.checkProductsAvailability(productQuantities);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id,productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductEntity> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}