package com.shopsupport.supportservice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "MESSAGES")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    @Column(name="TICKET_ID")
    int ticketId;
    @Column(name="MESSAGE")
    String message;
    @Column(name="SENDER_ID")
    int senderId;
    @Column(name="MESSAGE_DATE")
    Timestamp messageDate;
}
