package com.shopsupport.supportservice.controllers;

import com.shopsupport.supportservice.dto.MessageDTO;
import com.shopsupport.supportservice.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class WebSocketController {

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/sendMessage/{ticketId}")
    @SendTo("/topic/{ticketId}")
    public MessageDTO sendMessage(@Payload MessageDTO message) {
        // Zapisz nową wiadomość do bazy danych
        messageRepository.addMessage(message);
        return message;
    }

    @MessageMapping("/getMessages/{ticketId}")
    @SendTo("/topic/{ticketId}")
    public List<MessageDTO> getMessages(@Payload Map<String, Integer> payload) {
        int ticketId = payload.get("ticketId");
        // Pobierz historię konwersacji dla danego TICKET_ID
        return messageRepository.getMessagesByTicketIdOrderByDate(ticketId);
    }
}
