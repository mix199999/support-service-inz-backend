package com.shopsupport.supportservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ORDER_ID", nullable = false)
    private int orderId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private int productId;

    @Column(name = "UNIT_PRICE", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
    private Product product;

}
