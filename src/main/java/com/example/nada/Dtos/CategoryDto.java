package com.example.nada.Dtos;

import com.example.nada.Models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {
    private static final long serialVersionUid=1l;
    private UUID id;
    private String name;

    public CategoryDto(Category category){
        this.id=category.getId();
        this.name= category.getName();
    }
}
