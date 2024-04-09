package com.tiendavirtual.mascotas.controller;

import com.tiendavirtual.mascotas.entity.dto.ProductDto;
import com.tiendavirtual.mascotas.service.ProductService;
import com.tiendavirtual.mascotas.utils.ApiResponse;

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

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(service.save(productDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(service.findAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable(name = "productId") String productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable(name = "productId") String productId) {
        return ResponseEntity.ok(service.delete(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable(name = "productId") String productId,
            @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(service.update(productId, productDto));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> searchProduct(
            @PathVariable(name = "name") String name) {
        return ResponseEntity.ok(service.findAllProductsByName(name));
    }
}
