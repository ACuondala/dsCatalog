package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDto;
import com.example.nada.Models.Category;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Services.CategoryService;
import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @Operation(summary = "List all Categories")
    @GetMapping
    public ResponseEntity<Page<CategoryDto>> index(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value="direction", defaultValue = "ASC") String direction,
            @RequestParam(value="orderBy", defaultValue = "name") String orderBy

    ){
        //List<CategoryDto> categories= service.getAll();
        PageRequest pageRequest=PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<CategoryDto> categories=service.getAll(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @Operation(summary="Return a sigle category")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> show(@PathVariable UUID id){
        CategoryDto categoryDto=service.getCategory(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(categoryDto);
    }

    @Operation(summary="Create a new Category")
    @PostMapping
   public ResponseEntity<CategoryDto> store(@RequestBody  CategoryDto categorydto){
        categorydto=service.saveCategory(categorydto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorydto);
    }
    @Operation(summary = "Update a expecific category")
    @PutMapping(value="/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable UUID id, @RequestBody CategoryDto categoryDto){
    categoryDto=service.update(id,categoryDto);
    return ResponseEntity.status(HttpStatus.OK).body(categoryDto);

    }
    @Operation(summary = "Delete a category")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<CategoryDto> destroy(UUID id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
