package com.shopsupport.supportservice.services;


import com.shopsupport.supportservice.dto.TicketDTO;
import com.shopsupport.supportservice.entities.Ticket;
import com.shopsupport.supportservice.mappers.TicketMapper;
import com.shopsupport.supportservice.mappers.UserMapper;
import com.shopsupport.supportservice.repositories.TicketRepository;
import com.shopsupport.supportservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TicketService {


    private final TicketRepository ticketRepository;


    private final TicketMapper ticketMapper;


}
