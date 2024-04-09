package com.tiendavirtual.mascotas.entity.repository;

import com.tiendavirtual.mascotas.entity.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByName(String name);

    List<Product> findAllProductsByName(String name);
}
