package com.shopsupport.supportservice.repositories;

import com.shopsupport.supportservice.dto.TicketDTO;
import com.shopsupport.supportservice.entities.Message;
import com.shopsupport.supportservice.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TicketRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<TicketDTO> findAllTickets() {
        String sql = "SELECT  TICKET_ID AS idTicket, TICKET_TITLE AS titleTicket, USER_ID, ORDER_ID, HANDLER_ID," +
                " STATUS FROM TICKETS";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketDTO.class));
    }

    public List<TicketDTO> findTicketsByUserId(int userId) {
        String sql = "SELECT TICKET_ID AS idTicket, TICKET_TITLE AS titleTicket, USER_ID, ORDER_ID, HANDLER_ID, STATUS FROM TICKETS WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketDTO.class), userId);
    }

    public List<TicketDTO> findTicketsByHandlerId(int handlerId) {
        String sql = "SELECT TICKET_ID AS idTicket, TICKET_TITLE AS titleTicket, USER_ID, ORDER_ID, HANDLER_ID, STATUS FROM TICKETS WHERE HANDLER_ID = ? AND STATUS <> 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketDTO.class), handlerId);
    }




    public int addTicket(Ticket ticket) {
        String sql = "INSERT INTO TICKETS (TICKET_TITLE, USER_ID, ORDER_ID, HANDLER_ID, STATUS) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ticket.getTitleTicket());
            preparedStatement.setInt(2, ticket.getUserId());
            preparedStatement.setInt(3, ticket.getOrderId());
            preparedStatement.setInt(4, ticket.getHandlerId());
            preparedStatement.setBoolean(5, ticket.getStatus());
            return preparedStatement;
        }, keyHolder);

        // Retrieve the generated TICKET_ID
        int ticketId = keyHolder.getKey().intValue();

        return ticketId;
    }

    public List<TicketDTO> findTicketsByStatus(int status) {
        String sql = "SELECT TICKET_ID AS idTicket, TICKET_TITLE AS titleTicket, USER_ID, ORDER_ID, HANDLER_ID, STATUS FROM TICKETS WHERE STATUS = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketDTO.class), status);
    }



    public int updateHandlerId(int ticketId, int newHandlerId) {
        String sql = "UPDATE TICKETS SET HANDLER_ID = ? WHERE TICKET_ID = ?";
        return jdbcTemplate.update(sql, newHandlerId, ticketId);
    }

    public int updateStatus(int ticketId, boolean newStatus) {
        String sql = "UPDATE TICKETS SET STATUS = ? WHERE TICKET_ID = ?";
        return jdbcTemplate.update(sql, newStatus, ticketId);
    }

    public int updateTicketStatusToTrue(int ticketId) {
        String sql = "UPDATE TICKETS SET STATUS = true WHERE TICKET_ID = ?";
        return jdbcTemplate.update(sql, ticketId);
    }


}

