package com.shopsupport.supportservice.repositories;

import com.shopsupport.supportservice.dto.OrderDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderDetailsDTO> findAll() {
        String query = "SELECT od.id, od.order_id, od.product_id, od.unit_price, od.quantity, p.product_name " +
                "FROM ORDER_DETAILS od " +
                "JOIN PRODUCTS p ON od.product_id = p.product_id";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrderDetailsDTO.class));
    }

    public List<OrderDetailsDTO> findByOrderId(int orderId) {
        String query = "SELECT od.id, od.order_id, od.product_id, od.unit_price, od.quantity, p.product_name " +
                "FROM ORDER_DETAILS od " +
                "JOIN PRODUCTS p ON od.product_id = p.product_id " +
                "WHERE od.order_id = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrderDetailsDTO.class), orderId);
    }

    // Dodaj inne metody zapytań SQL według potrzeb
}
