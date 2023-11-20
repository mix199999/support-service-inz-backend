
package com.shopsupport.supportservice.repositories;


import com.shopsupport.supportservice.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
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

     public boolean deleteUserById(int id) {
          if (findById(id) != null) {
               String query = "DELETE FROM USERS WHERE USER_ID = ?";
               try {
                    int rowsAffected = jdbcTemplate.update(query, id);
                    return rowsAffected > 0;
               } catch (DataAccessException e) {
                    // Handle any potential exceptions, log them, or return false
                    return false;
               }
          } else {
               return false;
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



     public ResponseEntity<String> deleteRecord(int userId) {
          try {
               if (hasRelatedOrders(userId) || hasRelatedTickets(userId)) {
                    deleteRelatedRecords(userId);

                    return deleteUserById(userId) ?
                            ResponseEntity.ok("Deletion successful") :
                            new ResponseEntity<>("Deletion failed", HttpStatus.INTERNAL_SERVER_ERROR);
               } else {
                    return deleteUserById(userId) ?
                            ResponseEntity.ok("Deletion successful") :
                            new ResponseEntity<>("Deletion failed", HttpStatus.INTERNAL_SERVER_ERROR);
               }
          } catch (DataAccessException e) {
               return new ResponseEntity<>("Deletion failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
     }

     private void deleteRelatedRecords(int userId) {
          if (hasRelatedOrders(userId)) {
               deleteFromOrders(userId);
          }

          if (hasRelatedTickets(userId)) {
               deleteFromMessagesAndTickets(userId);
          }
     }



     public boolean hasRelatedOrders(int userId) {
          String query = "SELECT COUNT(*) FROM ORDERS WHERE USER_ID = ?";

         return jdbcTemplate.queryForObject(query, Integer.class, userId) != null;
     }

     public boolean hasRelatedTickets(int userId){
          String query = "SELECT COUNT(*) FROM TICKETS WHERE USER_ID = ?";

          return jdbcTemplate.queryForObject(query, Integer.class, userId) != null;
     }


     public boolean deleteFromUsers(int value){
          String query = "DELETE FROM USERS WHERE USER_ID = ?";
          try {
               int rowsAffected = jdbcTemplate.update(query, value);
               if (rowsAffected > 0) {
                    return true;
               } else {
                    return false;
               }
          } catch (DataAccessException e) {
               return false;
          }
     }
     public boolean deleteFromOrders(int userId) {
          try {
               // Step 1: Delete from ORDER_DETAILS
               String deleteOrderDetailsQuery = "DELETE FROM ORDER_DETAILS WHERE ORDER_ID IN (SELECT ORDER_ID FROM ORDERS WHERE USER_ID = ?)";
               jdbcTemplate.update(deleteOrderDetailsQuery, userId);

               // Step 2: Delete from ORDERS
               String deleteOrdersQuery = "DELETE FROM ORDERS WHERE USER_ID = ?";
               int rowsAffected = jdbcTemplate.update(deleteOrdersQuery, userId);

               // Return true if any rows were affected in either table
               return rowsAffected > 0;
          } catch (DataAccessException e) {
               // Handle the exception or rethrow if needed
               return false;
          }
     }
     public boolean deleteFromMessagesAndTickets(int userId) {
          try {
               // Step 1: Delete from MESSAGES
               String deleteMessagesQuery = "DELETE FROM MESSAGES WHERE SENDER_ID = ?";
               jdbcTemplate.update(deleteMessagesQuery, userId);

               // Step 2: Delete from TICKETS
               String deleteTicketsQuery = "DELETE FROM TICKETS WHERE USER_ID = ? OR HANDLER_ID = ?";
               int rowsAffected = jdbcTemplate.update(deleteTicketsQuery, userId, userId);

               // Return true if any rows were affected in either table
               return rowsAffected > 0;
          } catch (DataAccessException e) {
               // Handle the exception or rethrow if needed
               return false;
          }
     }




}

