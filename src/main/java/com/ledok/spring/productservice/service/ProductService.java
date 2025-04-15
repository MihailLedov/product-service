package com.ledok.spring.productservice.service;

import com.ledok.spring.productservice.controller.dto.ProductDto;
import com.ledok.spring.productservice.controller.dto.ProductFilter;
import com.ledok.spring.productservice.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductEntity createProduct(ProductDto productDto);
        Page<ProductDto> getAllProducts(ProductFilter filter, Pageable pageable);
        ProductDto getProductById(Long id);
        List<ProductDto> getProductsByIds(List<Long> ids);
        boolean checkProductsAvailability(Map<Long, Integer> productQuantities);
    }
//    // Основные CRUD операции
//    ProductResponse createProduct(ProductRequest productRequest);
//    ProductResponse getProductById(Long id);
//    Page<ProductResponse> getAllProducts(Pageable pageable);
//    ProductResponse updateProduct(Long id, ProductRequest productRequest);
//    void deleteProduct(Long id);
//    // Фильтрация и поиск
//    Page<ProductResponse> searchProducts(String name,BigDecimal minPrice,BigDecimal maxPrice,String category,Pageable pageable);
//    // Специфичные бизнес-методы
////    boolean existsById(Long id);
//    List<ProductResponse> getProductsByIds(List<Long> ids);
//    void updateStock(Long productId, int quantityChange);
