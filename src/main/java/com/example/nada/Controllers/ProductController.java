package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDto;
import com.example.nada.Dtos.ProductDto;
import com.example.nada.Services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value="/products")
@Tag(name = "Products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Show all products")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> index(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerpage", defaultValue="10") Integer linesPerPage,
            @RequestParam(value="direction", defaultValue="ASC") String direction,
            @RequestParam(value="orderBy", defaultValue = "name") String orderBy

            ){
        PageRequest pageRequest=PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<ProductDto> productDtos= service.index(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productDtos);

    }

    @Operation(summary="Return a Single product")
    @GetMapping(value="/{id}")
    public ResponseEntity<ProductDto> show(@PathVariable UUID id){
        ProductDto productDto= service.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(productDto);
    }

    @Operation(summary = "Create new Product")
    @PostMapping
    public ResponseEntity<ProductDto> store(@RequestBody ProductDto productDto){

        ProductDto productDto1=service.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(productDto1);
    }

    @Operation(summary = "Update an existenty product")
    @PutMapping(value="/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable UUID id, @RequestBody ProductDto productDto ){
        ProductDto productDto1=service.updateProduct(id,productDto);
        return ResponseEntity.status(HttpStatus.OK).body(productDto1);
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<ProductDto> destroy(UUID id){
       service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
