package com.shopsupport.supportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDetailsDTO {

    private int id;

    private int orderId;

    private int productId;

    private BigDecimal unitPrice;

    private int quantity;

    private String productName; // Nowe pole
}
