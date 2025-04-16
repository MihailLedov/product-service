package com.ledok.spring.productservice.service;

import com.ledok.spring.productservice.advice.ProductNotFoundException;
import com.ledok.spring.productservice.controller.dto.ProductDto;
import com.ledok.spring.productservice.controller.dto.ProductFilter;
import com.ledok.spring.productservice.jpa.entity.ProductEntity;
import com.ledok.spring.productservice.jpa.repository.ProductRepository;
import com.ledok.spring.productservice.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductEntity createProduct(ProductDto productDto) {
        ProductEntity productEntity = productMapper.toEntity(productDto);
        return productRepository.save(productEntity);
    }

    @Override
    public Page<ProductDto> getAllProducts(ProductFilter filter, Pageable pageable) {
        Page<ProductEntity> products = productRepository.findAllWithFilter(
                filter.getName(),
                filter.getCategory(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                pageable);
        return products.map(productMapper::toDto);
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkProductsAvailability(Map<Long, Integer> productQuantities) {
        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            ProductEntity product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ProductNotFoundException("Продукт не найден"));
            if (product.getStock() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        if (productRepository.existsById(id)) {
            throw new ProductNotFoundException("Продукт не найден");
        }
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductEntity productEntity = product.get();
            productEntity.setId(productDto.getId());
            productEntity.setName(productDto.getName());
            productEntity.setDescription(productDto.getDescription());
            productEntity.setCategory(productDto.getCategory());
            productEntity.setPrice(productDto.getPrice());
            productEntity.setStock(productDto.getStock());
            return productMapper.toDto(productRepository.save(productEntity));
        }
        throw new ProductNotFoundException("Продукт не найден");
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id));
    }
}
//    private final ProductRepository productRepository;
//    private final ProductMapper productMapper;
//
//    @Override
//    @Transactional
//    public ProductResponse createProduct(ProductRequest productRequest) {
//        ProductEntity productEntity = productMapper.toEntity(productRequest);
//        return productMapper.toResponse(productRepository.save(productEntity));
//    }
//
//    @Override
//    @Transactional
//    public ProductResponse getProductById(Long id) {
//        return productMapper.toResponse(productRepository.findById(id).orElse(null));
//    }
//
//    @Override
//    @Transactional
//    public Page<ProductResponse> getAllProducts(Pageable pageable) {
//        return productRepository.findAll(pageable).map(productMapper::toResponse);
//    }
//
//    @Override
//    @Transactional
//    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
//        ProductEntity productEntity = productRepository.findById(id).orElse(null);
//        if (productEntity == null) {
//            throw new ProductNotFoundException("Продукт с ID: "+id+" не найден!");
//        }
//        productMapper.updateFromRequest(productRequest, productEntity);
//        return productMapper.toResponse(productRepository.save(productEntity));
//    }
//
//    @Override
//    @Transactional
//    public void deleteProduct(Long id) {
//        if (!productRepository.existsById(id)) {
//            throw new ProductNotFoundException("Продукт с ID: "+id+" не найден!");
//        }
//        productRepository.deleteById(id);
//
//    }
//
//    @Override
//    @Transactional
//    public Page<ProductResponse> searchProducts(
//            String name,
//            BigDecimal minPrice,
//            BigDecimal maxPrice,
//            String category,
//            Pageable pageable) {
//
//            // Используем спецификации
//            Specification<ProductEntity> spec = Specification.where(null);
//
//            if (StringUtils.hasText(name)) {
//                spec = spec.and((root, query, cb) ->
//                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
//            }
//
//            if (minPrice != null) {
//                spec = spec.and((root, query, cb) ->
//                        cb.greaterThanOrEqualTo(root.get("price"), minPrice));
//            }
//
//            if (maxPrice != null) {
//                spec = spec.and((root, query, cb) ->
//                        cb.lessThanOrEqualTo(root.get("price"), maxPrice));
//            }
//
//            if (StringUtils.hasText(category)) {
//                spec = spec.and((root, query, cb) ->
//                        cb.equal(root.get("category"), category));
//            }
//
//            return productRepository.findAll(spec, pageable)
//                    .map(productMapper::toResponse);
//        }
//
//    @Override
//    @Transactional
//    public List<ProductResponse> getProductsByIds(List<Long> ids) {
//        List<ProductEntity> productEntities = productRepository.findAllById(ids);
//        return productEntities.stream().map(productMapper::toResponse).collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public void updateStock(Long productId, int quantityChange) {
//        ProductEntity productEntity = productRepository.findById(productId)
//                .orElseThrow(()-> {throw new ProductNotFoundException("Продукт с ID: "+productId+" не найден!");});
//        int newQuantity = productEntity.getStock() + quantityChange;
//        productEntity.setStock(newQuantity);
//        productRepository.save(productEntity);
//    }
