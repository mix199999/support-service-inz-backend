package com.shopsupport.supportservice.repositories;

import com.shopsupport.supportservice.entities.FAQ;
import com.shopsupport.supportservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class FAQRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    private static class FAQRowMapper implements RowMapper<FAQ> {
        @Override
        public FAQ mapRow(ResultSet rs, int rowNum) throws SQLException {
            FAQ faq = new FAQ();
            faq.setId(rs.getInt("FAQ_ID"));
            faq.setProductId(rs.getInt("PRODUCT_ID"));
            faq.setTitle(rs.getString("INFO_TITLE"));
            faq.setText(rs.getString("INFO_TEXT"));
            faq.setProductName(rs.getString("PRODUCT_NAME"));
            return faq;
        }
    }
    public List<FAQ> findAll() {
        String query = "SELECT FAQ_ID, PRODUCT_ID, INFO_TITLE, INFO_TEXT FROM FAQ";
        return jdbcTemplate.query(query,new FAQRowMapper());
    }


    public void deleteById(int faqId) {
        String query = "DELETE FROM FAQ WHERE FAQ_ID = ?";
        jdbcTemplate.update(query, faqId);
    }
    public List<FAQ> getAllFAQ() {
        try {
            return jdbcTemplate.query("SELECT * FROM FAQ", new BeanPropertyRowMapper<>(FAQ.class));
        } catch (DataAccessException e) {
            // Obsługa wyjątku - możesz zalogować błąd lub rzucać własne wyjątki
            e.printStackTrace(); // lub logger.error("Błąd przy pobieraniu FAQ", e);
            return Collections.emptyList(); // Zwróć pustą listę lub rzuć własny wyjątek, zależnie od potrzeb
        }



    }

    public List<FAQ> getFAQByProductId(int productId) {
        try {
            String query = "SELECT * FROM FAQ WHERE PRODUCT_ID = ?";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(FAQ.class), productId);
        } catch (DataAccessException e) {
            // Obsługa wyjątku, na przykład logowanie
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<FAQ> getFAQByProductName(String productName) {
        try {
            String query = "SELECT f.* FROM FAQ f JOIN PRODUCTS p ON f.PRODUCT_ID = p.PRODUCT_ID WHERE p.PRODUCT_NAME = ?";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(FAQ.class), productName);
        } catch (DataAccessException e) {
            // Obsługa wyjątku, na przykład logowanie
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<FAQ> getAllFAQWithProductName() {
        try {
            String query = "SELECT f.*, p.PRODUCT_NAME FROM FAQ f JOIN PRODUCTS p ON f.PRODUCT_ID = p.PRODUCT_ID";
            return jdbcTemplate.query(query, new FAQRowMapper());
        } catch (DataAccessException e) {
            // Obsługa wyjątku, na przykład logowanie
            e.printStackTrace();
            return Collections.emptyList();
        }
    }



    public void save(FAQ faq) {
        String query = "INSERT INTO FAQ (PRODUCT_ID, INFO_TITLE, INFO_TEXT) VALUES (?, ?, ?)";

        jdbcTemplate.update(query, faq.getProductId(), faq.getTitle(), faq.getText());
    }






}
