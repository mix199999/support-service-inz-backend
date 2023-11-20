package com.shopsupport.supportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TicketDTO implements Serializable {

    private int idTicket;

    private String titleTicket;

    private Integer userId;

    private Integer orderId;

    private Integer handlerId;

    private Boolean status;
}

