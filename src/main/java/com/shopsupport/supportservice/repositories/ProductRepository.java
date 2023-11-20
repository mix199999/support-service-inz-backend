package com.shopsupport.supportservice.repositories;

import com.shopsupport.supportservice.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("PRODUCT_ID"));
            product.setName(rs.getString("PRODUCT_NAME"));
            return product;
        }
    }

    public List<Product> getAllProducts() {
        try {
            return jdbcTemplate.query("SELECT * FROM PRODUCTS", new ProductRowMapper());
        } catch (Exception e) {
            // Obsługa wyjątku, na przykład logowanie
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public Product getProductById(int productId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?",
                    new BeanPropertyRowMapper<>(Product.class), productId);
        } catch (Exception e) {
            // Obsługa wyjątku, na przykład logowanie
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> getProductsByName(String productName) {
        try {
            return jdbcTemplate.query("SELECT * FROM PRODUCTS WHERE PRODUCT_NAME = ?",
                    new BeanPropertyRowMapper<>(Product.class), productName);
        } catch (Exception e) {
            // Obsługa wyjątku, na przykład logowanie
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}