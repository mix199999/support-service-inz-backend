package com.shopsupport.supportservice.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FAQ")
@Data
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQ_ID")
    private int id;

    @Column(name = "PRODUCT_ID")
    private int productId;

    @Column(name = "INFO_TITLE")
    private String title;

    @Column(name = "INFO_TEXT")
    private String text;

    private String productName;
}
