package com.shopsupport.supportservice.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDTO implements Serializable {

    private Integer id;
    private  Integer ticketId;
    private String message;
    private  Integer senderId;
    private Timestamp messageDate;

}
