package com.example.nada.Dtos;

import com.example.nada.Models.Category;
import com.example.nada.Models.Product;
import jakarta.persistence.*;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import  java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProductDto implements Serializable {
    private static final long serialVersionUID=1L;

    private UUID id;

    private String name;
    // @Column(columnDefinition="Text") permite modificar o campo para ele receber textos longos

    private String description;

    private Double price;

    private String imgUrl;

    private Instant date;


    private List<CategoryDto> category_dto= new ArrayList<>();

    public ProductDto(){}

    public ProductDto(UUID id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;

    }

    public ProductDto(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }
    //Construtar que recebe categorias e adiciona na lista category_dto
    //quando ser instaciado ele vai adicionar cada elemento do set categories e vai inserir cada category em um novo category list
    public ProductDto(Product entity, Set<Category> categories){
        this(entity);
        //percorrer cada category e inseri na lista com categoryDTo

        //Funcção de alta ordem .foreach(), para cada cat ele vair na list<CategoryDto> ele vai adicionar novo elemento transformado em dto
        categories.forEach(cat->this.category_dto.add(new CategoryDto(cat)));

        /*for(Category cat: categories){
            this.category_dto.add(new CategoryDto(cat));
        }*/
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<CategoryDto> getCategory_dto() {
        return category_dto;
    }
}
