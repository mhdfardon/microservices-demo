package com.microservices.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
//@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Product {

    @JsonIgnore
//    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private String description;

    private Double price;

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
