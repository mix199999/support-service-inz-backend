package com.shopsupport.supportservice.mappers;


import com.shopsupport.supportservice.dto.TicketDTO;
import com.shopsupport.supportservice.entities.Ticket;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "idTicket", target = "idTicket")
    @Mapping(source = "titleTicket", target = "titleTicket", defaultValue = "N/A")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "handlerId", target = "handlerId")
    @Mapping(source = "status", target = "status")
    TicketDTO ticketToDTO(Ticket ticket);
}





