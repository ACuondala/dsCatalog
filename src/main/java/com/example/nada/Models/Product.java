package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="products")
@Getter
@NoArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Setter
    private String name;
    // @Column(columnDefinition="Text") permite modificar o campo para ele receber textos longos
    @Setter
    @Column(columnDefinition="Text")
    private String description;
    @Setter
    private Double price;
    @Setter
    private String imgUrl;

    //@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") VAI instruir a nossa BD para registrar a data no formato utc

    @Setter
    @Column(columnDefinition="TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;

    //Quando se usa o set quer dizer que ele não vai aceitar repetições
    //Para não ter regsitro de um mesmo produto com uma categoria repetido
    //Hash set é uma das classes que implementa o set
    //@JoinTable: faz a ssociação entre dua entidades
    //name: especifica o nome da tabela pivot
    // joinColumns: ele vai especificar as duas chaves estrangeira(product_id, category_id)
    // o primeiro @JoinColumn: ele vai estabelecer qual vai ser a chave estrangeira da classe product, name:nome da chave estrangeira
    //inverseJoinColumns: estabelece a outra chave estrangeira que faz a associação de muitos para muitos, ele pega o tipo que estiver na coleção(set ou list)

    @ManyToMany
    @JoinTable(
            name="category_product",
            joinColumns=@JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")

    )
    private Set<Category> category= new HashSet<>();

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant CreatedAt;
    @Column(columnDefinition="TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    public Product(String name, String description, Double price, String imgUrl, Instant date) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public Set<Category>getCategory(){
        return category;
    }
}
