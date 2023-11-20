package com.shopsupport.supportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {


    private int orderId;


    private int userId;

    private Date orderDate;
}