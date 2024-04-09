package com.tiendavirtual.mascotas.service;

import com.tiendavirtual.mascotas.entity.dto.ProductDto;
import com.tiendavirtual.mascotas.entity.mapper.ProductMapper;
import com.tiendavirtual.mascotas.entity.model.Product;
import com.tiendavirtual.mascotas.entity.repository.ProductRepository;
import com.tiendavirtual.mascotas.exception.ProductNotFoundException;
import com.tiendavirtual.mascotas.utils.ApiResponse;
import com.tiendavirtual.mascotas.utils.Message;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto findById(String productId) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(
                                () ->
                                        new ProductNotFoundException(
                                                Message.PRODUCT_NOT_FOUND,
                                                404,
                                                HttpStatus.NOT_FOUND,
                                                LocalDateTime.now()));

        return ProductMapper.toDto(product);
    }

    public List<ProductDto> findAllProducts() {

        return productRepository.findAll().stream()
                .map(
                        (dto) ->
                                new ProductDto(
                                        dto.getId(),
                                        dto.getName(),
                                        dto.getDescription(),
                                        dto.getPrice()))
                .toList();
    }

    public ApiResponse delete(String productId) {

        ProductDto productDto = findById(productId);

        if (productDto == null) {

            throw new ProductNotFoundException(
                    Message.PRODUCT_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now());
        }

        Product product = new Product();
        product.setId(productId);
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());

        productRepository.delete(product);

        return new ApiResponse(
                Message.PRODUCT_DELETE_SUCCESSFULLY,
                204,
                HttpStatus.NO_CONTENT,
                LocalDateTime.now());
    }

    public ApiResponse save(ProductDto dto) {

        Product product = new Product();
        product.setDescription(dto.description());
        product.setName(dto.name());
        product.setPrice(dto.price());

        productRepository.save(product);

        return new ApiResponse(
                Message.PRODUCT_SAVE_SUCCESSFULLY, 201, HttpStatus.CREATED, LocalDateTime.now());
    }

    public ApiResponse update(String productId, ProductDto dto) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(
                                () ->
                                        new ProductNotFoundException(
                                                Message.PRODUCT_NOT_FOUND,
                                                404,
                                                HttpStatus.NOT_FOUND,
                                                LocalDateTime.now()));
        product.setDescription(dto.description());
        product.setName(dto.name());
        product.setPrice(dto.price());

        productRepository.save(product);

        return new ApiResponse(
                Message.PRODUCT_UPDATE_SUCCESSFULLY, 201, HttpStatus.OK, LocalDateTime.now());
    }

    public List<ProductDto> findAllProductsByName(String name) {

        List<Product> products = productRepository.findAllProductsByName(name);

        if (!products.isEmpty()) {
            return products.stream()
                    .map(
                            (dto) ->
                                    new ProductDto(
                                            dto.getId(),
                                            dto.getName(),
                                            dto.getDescription(),
                                            dto.getPrice()))
                    .toList();
        }

        throw new ProductNotFoundException(
                Message.PRODUCT_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }
}
