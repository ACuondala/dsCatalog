package com.example.nada.Services;

import com.example.nada.Dtos.CategoryDto;
import com.example.nada.Models.Category;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Services.Exceptions.EntitiesNotFoundException;
import com.example.nada.Services.Exceptions.IntegrityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly=true)
    public Page<CategoryDto> getAll(PageRequest pageRequest){
        Page<Category> categories=repository.findAll(pageRequest);

        //o Page já é um stram do java 8 não vamos precisr adicionar mais o ".stream()", nem o collect no final ".collect(Collectors.toList())"
        return categories.map(x->new CategoryDto(x));
    }

    @Transactional(readOnly=true)
    public CategoryDto getCategory(UUID id){
        Optional<Category> _category=repository.findById(id);
        Category category=_category.orElseThrow(()-> new EntitiesNotFoundException("Category Not Found With ID= "+id));
        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto saveCategory(CategoryDto categorydto){
        Category category=new Category();
        category.setName(categorydto.getName());
        category=repository.save(category);
        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto update(UUID id, CategoryDto categoryDto){
        //if it's Long or int we're use the method getOne();
        //if it's UUID we're use the method getReferenceById();
        try {
            Category category = repository.getReferenceById(id);
            category.setName(categoryDto.getName());
            category = repository.save(category);
            return new CategoryDto(category);
        }catch(EntityNotFoundException e){
            throw new EntitiesNotFoundException("Id not Found: "+id);
        }
    }

    public void delete(UUID id) {
        //no metodo delete não se adiciona o Transactional

        try{
            Optional<Category>category=repository.findById(id);
            if(category.isPresent()){

                Category _category=category.get();
                repository.delete(_category);
            }

        }catch(EmptyResultDataAccessException e){
            throw new EntitiesNotFoundException("Id not found: "+id);
        }catch(DataIntegrityViolationException e){
            // DataIntegrityViolationException is using when you try to delete category with data
            //Erro de integridade
            //vamos criar uma nova exception no pacote exception do service
            //adicionar um novo method na exception global ControllerExceptionHandler

            throw new IntegrityException("Dat");
        }
    }
}
