package com.shopsupport.supportservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "ORDERS")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private int orderId;

    @Column(name="USER_ID")
    private int userId;
    @Column(name="ORDER_DATE")
    private Date orderDate;
}