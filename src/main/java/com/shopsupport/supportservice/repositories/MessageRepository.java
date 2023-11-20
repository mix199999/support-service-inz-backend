package com.shopsupport.supportservice.repositories;

import com.shopsupport.supportservice.dto.MessageDTO;
import com.shopsupport.supportservice.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MessageRepository  {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<MessageDTO> getAll(){
        String query= "SELECT * FROM MESSAGES";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(MessageDTO.class));
    }

    public void addMessage(Message message) {
        String sql = "INSERT INTO MESSAGES (TICKET_ID, MESSAGE, SENDER_ID, MESSAGE_DATE) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getTicketId());
            preparedStatement.setString(2, message.getMessage());
            preparedStatement.setInt(3, message.getSenderId());
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(message.getMessageDate().getTime()));
            return preparedStatement;
        });
    }

    public List<MessageDTO> getMessagesByUserId(int userId) {
        String query = "SELECT * FROM MESSAGES WHERE SENDER_ID = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new BeanPropertyRowMapper<>(MessageDTO.class));
    }

}
