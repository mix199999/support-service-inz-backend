package com.shopsupport.supportservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TICKETS")
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private int idTicket;

    @Column(name = "TICKET_TITLE")
    private String titleTicket;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "HANDLER_ID")
    private Integer handlerId;

    @Column(name = "STATUS")
    private Boolean status;
}
