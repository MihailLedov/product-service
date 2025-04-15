package com.ledok.spring.productservice.mapper;

import com.ledok.spring.productservice.controller.dto.ProductDto;
import com.ledok.spring.productservice.jpa.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .category(entity.getCategory())
                .build();
    }

    public ProductEntity toEntity(ProductDto dto) {
        return ProductEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(dto.getCategory())
                .build();
    }
}

//    public ProductEntity toEntity(ProductRequest productRequest) {
//        if (productRequest == null) {
//            return null;
//        }
//
//        return ProductEntity.builder()
//                .name(productRequest.getName())
//                .description(productRequest.getDescription())
//                .price(productRequest.getPrice())
//                .stock(productRequest.getStock())
//                .build();
//    }
//
//    public ProductResponse toResponse(ProductEntity productEntity) {
//        if (productEntity == null) {
//            return null;
//        }
//
//        return ProductResponse.builder()
//                .id(productEntity.getId())
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .price(productEntity.getPrice())
//                .stock(productEntity.getStock())
//                .build();
//    }
//
//    public void updateFromRequest(ProductRequest productRequest, ProductEntity productEntity) {
//        if (productRequest == null || productEntity == null) {
//            return;
//        }
//
//        productEntity.setName(productRequest.getName());
//        productEntity.setDescription(productRequest.getDescription());
//        productEntity.setPrice(productRequest.getPrice());
//        productEntity.setStock(productRequest.getStock());
//    }
