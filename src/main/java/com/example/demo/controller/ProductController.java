package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.ProductEntity;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal String userId, @RequestBody ProductDTO dto) {
        try {
            String temporaryUserId = "GoEunPark"; //temporary user id.

            ProductEntity entity = ProductDTO.toEntity(dto);

            entity.setId(null);

            entity.setUserId(userId);

            List<ProductEntity> entities = service.create(entity);

            List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);

        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveProductList(@AuthenticationPrincipal String userId) {
        List<ProductEntity> entities = service.retrieve(userId);

        List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

        ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductList(@RequestParam("title") String title) {
        String temporaryUserId = "GoEunPark"; //temporary user id.


        Optional<ProductEntity> entities = service.search(title);

        List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

        ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(
            @AuthenticationPrincipal String userId,
            @RequestBody ProductDTO dto){
        try{
            String temporaryUserId = "GoEunPark";

            ProductEntity entity = ProductDTO.toEntity(dto);

            entity.setUserId(userId);

            List<ProductEntity> entities = service.delete(entity);

            List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping
    public ResponseEntity<?> updateProduct(
            @AuthenticationPrincipal String userId,
            @RequestBody ProductDTO dto) {
        try {

            ProductEntity entity = ProductDTO.toEntity(dto);

            entity.setUserId(userId);

            Optional<ProductEntity> entities = service.update(entity);

            List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
