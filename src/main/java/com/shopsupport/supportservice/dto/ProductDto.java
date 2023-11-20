package com.shopsupport.supportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class ProductDto implements Serializable {

    private int id;
    private String name;

    // Konstruktor bezparametrowy
    public ProductDto() {
    }

    // Konstruktor z parametrami
    public ProductDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Gettery i settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Metoda toString
    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
