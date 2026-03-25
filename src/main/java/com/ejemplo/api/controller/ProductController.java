package com.ejemplo.api.controller;

import com.ejemplo.api.model.ApiError;
import com.ejemplo.api.model.Product; // Importa el modelo generado para recibir datos
import com.ejemplo.api.model.ProductResponse; // Importa el modelo generado para responder
import com.ejemplo.api.service.ProductService;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid; // Permite la validación de los request bodies

@RestController
@RequestMapping("/api/v1") // Prefijo base de la API
@Validated // Activa la validación
public class ProductController {

    // Inyección de dependencias: Spring nos "pasa" una instancia del servicio
    @Autowired
    private ProductService productService;
    
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody Product productRequest) 
    {
        System.out.println("Controller - Product recibido: " + productRequest.getName());

        // El controller delega la lógica al servicio
        ProductResponse productResponse = productService.createProduct(productRequest);
    
        // Devolvemos la respuesta con el código 201 (CREATED)
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Integer productId) 
    {
        System.out.println("Controller - Buscando Product con ID: " + productId);

        // Validación básica del ID
        if (productId == null || productId < 1 || productId > 999999) {
            ApiError errorResponse = new ApiError();
            errorResponse.setCode(ApiError.CodeEnum.INVALID_INPUT);
            errorResponse.setMessage("Invalid product ID");
            errorResponse.setTimestamp(OffsetDateTime.now());
            errorResponse.setPath("/products/{productId}");

            Map<String, Object> details = new HashMap<>();
            details.put("info", "El valor proporcionado no es correcto, debe estar entre 1 y 999999");
            errorResponse.setDetails(details);

            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // El controller delega la lógica al servicio
        ProductResponse productResponse = productService.getProductById(productId.toString());

        // Devolvemos la consulta con código 200 (OK)
        return ResponseEntity.ok(productResponse);
    }
}