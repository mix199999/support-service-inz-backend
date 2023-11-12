
package com.shopsupport.supportservice.repositories;


import com.shopsupport.supportservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

     @Autowired
     JdbcTemplate jdbcTemplate;

     public List<User> findAll() {
          return jdbcTemplate.query("SELECT * FROM USERS", new BeanPropertyRowMapper<>(User.class));
     }

     public User findById(int id) {

          String query = "SELECT * FROM USERS WHERE USER_ID = ?";
          try{
               return jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(User.class), id);

          }
          catch (EmptyResultDataAccessException e)
          {
               return null;
          }
     }

     public User login(String username, String password) {
          String query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";

          try {
               return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), username, password);
          } catch (EmptyResultDataAccessException e) {
               return null;
          }
     }


     public User findByUsername(String username) {
          String query = "SELECT * FROM USERS WHERE USERNAME = ?";
          try{
               return jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(User.class), username);

          }
          catch (EmptyResultDataAccessException e)
          {
               return null;
          }
     }
}

