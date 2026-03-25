package com.ejemplo.api.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ejemplo.api.model.Category;
import com.ejemplo.api.model.Product;
import com.ejemplo.api.model.ProductResponse;

@Service
public class ProductService {

    public ProductResponse createProduct(Product product) 
    {
        System.out.println("Service - Product recibido: " + product.getName());

        // Simulamos la persistencia y generación de datos del sistema
        ProductResponse response = new ProductResponse();
        
        // Mapeo de campos desde el Request
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setDescription(product.getDescription());
        response.setInStock(product.getInStock());

        // Datos generados por el servidor (conforme al OAS)
        response.setId("prod-" + System.currentTimeMillis());
        response.setSku("SKU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        response.setCreatedAt(OffsetDateTime.now());
        response.setUpdatedAt(OffsetDateTime.now());

        return response;
    }

    public ProductResponse getProductById(String productId) 
    {
        System.out.println("Service - Buscando Product con ID: " + productId);

        // Simulación de búsqueda en base de datos
        ProductResponse response = new ProductResponse();
        response.setId(productId);
        response.setName("Producto de Ejemplo");
        response.setPrice(99.99);
        response.setCategory(Category.ELECTRONICS);
        response.setSku("LAP-GAM-001");
        response.setCreatedAt(OffsetDateTime.now());
        response.setInStock(true);
        
        return response;
    }
}