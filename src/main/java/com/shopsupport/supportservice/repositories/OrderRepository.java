package com.shopsupport.supportservice.repositories;

import com.shopsupport.supportservice.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<OrderDTO> getAll(){
        String query="SELECT * FROM ORDERS";
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(OrderDTO.class));
    }


    public List<OrderDTO> getOrdersById(int id){
        String query="SELECT * FROM ORDERS WHERE USER_ID =?";
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(OrderDTO.class), id);
    }


}
