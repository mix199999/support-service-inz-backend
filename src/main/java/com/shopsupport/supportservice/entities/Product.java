package com.shopsupport.supportservice.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    int id;
    @Column(name="PRODUCT_NAME")
    String name;

}
