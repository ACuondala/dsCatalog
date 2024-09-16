package com.example.nada.Services;

import com.example.nada.Dtos.CategoryDto;
import com.example.nada.Dtos.ProductDto;
import com.example.nada.Models.Category;
import com.example.nada.Models.Product;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Repositories.ProductRepository;
import com.example.nada.Services.Exceptions.EntitiesNotFoundException;
import com.example.nada.Services.Exceptions.IntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDto> index(PageRequest pageRequest){
        Page<Product> products=repository.findAll(pageRequest);
        return products.map(x-> new ProductDto(x));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(UUID id){
        Optional<Product> product=repository.findById(id);
        Product entity=product.orElseThrow(()-> new EntitiesNotFoundException("Product Not Found with id: "+id ));
        return new ProductDto(entity, entity.getCategory()
        );
    }
    @Transactional
    public ProductDto saveProduct(ProductDto productDto ){
        Product product= new Product();
        copyDtoToEntity(productDto,product);

        product= repository.save(product);

        return new ProductDto(product);
    }

    @Transactional
    public ProductDto updateProduct(UUID id,ProductDto productDto){
        try{
            Product product=repository.getReferenceById(id);
            copyDtoToEntity(productDto,product);

            product=repository.save(product);
            return new ProductDto(product);
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("Product not found with id: "+id);
        }
    }
    @Transactional

    public void delete(UUID id){
        try{
            Optional<Product> product= repository.findById(id);
            Product _product= product.get();
            repository.delete(_product);

        }catch(EmptyResultDataAccessException e){
            throw new EntitiesNotFoundException("Id: "+id+" Not Found" );
        }catch(DataIntegrityViolationException e) {
            throw new IntegrityException("this product is linked with another record on database");
        }
    }

    private void copyDtoToEntity(ProductDto dto, Product entity){

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategory().clear();
        for(CategoryDto categoryDto: dto.getCategory_dto()){
            Category category=categoryRepository.getReferenceById(categoryDto.getId());
            entity.getCategory().add(category);
        }
    }
}
