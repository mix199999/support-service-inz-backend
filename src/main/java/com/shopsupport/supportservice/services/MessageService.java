package com.shopsupport.supportservice.services;

import com.shopsupport.supportservice.config.WebSocketConfig;
import com.shopsupport.supportservice.dto.MessageDTO;
import com.shopsupport.supportservice.mappers.MessageMapper;
import com.shopsupport.supportservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageMapper messageMapper;


}