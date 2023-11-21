package com.shopsupport.supportservice.controllers;

import com.shopsupport.supportservice.applications.CustomAuthenticationProvider;
import com.shopsupport.supportservice.config.JwtTokenService;
import com.shopsupport.supportservice.dto.MessageDTO;
import com.shopsupport.supportservice.dto.OrderDTO;
import com.shopsupport.supportservice.dto.OrderDetailsDTO;
import com.shopsupport.supportservice.dto.TicketDTO;
import com.shopsupport.supportservice.entities.*;
import com.shopsupport.supportservice.mappers.TicketMapper;
import com.shopsupport.supportservice.repositories.*;

import com.shopsupport.supportservice.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        try {
            List<TicketDTO> tickets = ticketRepository.findAllTickets();

            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/messages-ticket")
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        try {
            List<MessageDTO> tickets = messageRepository.getAll();

            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


@Autowired
private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

@Autowired
        private TicketService ticketService;



    @Autowired
    private JwtTokenService jwtTokenService; // Dodaj to pole

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages() {
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }

    @GetMapping("/option-users")
    public ResponseEntity<List<User>> usersList(){
        return ResponseEntity.ok(userRepository.findAll());
    }
    @DeleteMapping("/option-users/delete/{value}")
    public ResponseEntity<ResponseEntity<String>> deleteRow(

            @PathVariable int value) {

       return  ResponseEntity.ok(userRepository.deleteRecord(value));
    }




    @GetMapping("/option-faq-show-all")
    public ResponseEntity<List<FAQ>> faqList() {
        return ResponseEntity.ok(faqRepository.getAllFAQWithProductName());
    }
    @DeleteMapping("/delete-faq/{faqId}")
    public ResponseEntity<String> deleteFAQ(@PathVariable int faqId) {
        try {
            faqRepository.deleteById(faqId);
            return ResponseEntity.ok("FAQ deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately, log or throw a custom exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete FAQ");
        }
    }



    @GetMapping("/orders/get-all-orders")
    public ResponseEntity<List<OrderDTO>>getAllOrders(){return ResponseEntity.ok(orderRepository.getAll());}
    @GetMapping("/orders/get-user-orders/{userId}")
    public ResponseEntity<?> getOrdersById(@PathVariable int userId) {
        List<OrderDTO> orders = orderRepository.getOrdersById(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("null");
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @GetMapping("/option-products")
    public ResponseEntity<List<Product>> productList() {
        return ResponseEntity.ok(productRepository.getAllProducts());
    }

    @GetMapping("/option-products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        return ResponseEntity.ok(productRepository.getProductById(productId));
    }

    @GetMapping("/option-products/product-name/{productName}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String productName) {
        return ResponseEntity.ok(productRepository.getProductsByName(productName));
    }

    @PostMapping("/submit-faq")
    public ResponseEntity<String> submitFAQ(@RequestBody FAQ formData) {
        try {
            // Save the form data to the database using the FAQRepository
            faqRepository.save(formData);

            return ResponseEntity.ok("FAQ submitted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately, log or throw a custom exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit FAQ");
        }
    }



    @GetMapping("/tickets/by-user/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByUserId(@PathVariable int userId) {
        List<TicketDTO> tickets = ticketRepository.findTicketsByUserId(userId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/tickets/by-handler/{handlerId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByHandlerId(@PathVariable int handlerId) {
        List<TicketDTO> tickets = ticketRepository.findTicketsByHandlerId(handlerId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @GetMapping("/order-details/all")
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrderDetails() {
        try {
            List<OrderDetailsDTO> orderDetails = orderDetailsRepository.findAll();

            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order-details/by-order-id/{orderId}")
    public ResponseEntity<List<OrderDetailsDTO>> getOrderDetailsByOrderId(@PathVariable int orderId) {
        try {
            List<OrderDetailsDTO> orderDetails = orderDetailsRepository.findByOrderId(orderId);

            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PostMapping("/ticket/add")
    public ResponseEntity<Map<String, Object>> addTicket(@RequestBody Ticket ticket) {
        // Add the ticket and retrieve the TICKET_ID
        int ticketId = ticketRepository.addTicket(ticket);

        // Get the current date and time
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = currentDate.format(formatter);

        // Prepare the response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("ticketId", ticketId);
        responseData.put("currentDate", formattedDate);

        // Return the response data
        return ResponseEntity.ok(responseData);
    }



    @PostMapping("/message/add")
    public ResponseEntity<String> addMessage(@RequestBody Message message) {
        messageRepository.addMessage(message);
        return ResponseEntity.ok("Message added successfully");
    }

    @GetMapping("/message/get/{userId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByUserId(@PathVariable int userId) {
        List<MessageDTO> messages = messageRepository.getMessagesByUserId(userId);
        return ResponseEntity.ok(messages);
    }


    @GetMapping("/tickets/not-handled")
    public ResponseEntity<List<TicketDTO>> getTicketsWithStatusZero() {

        return ResponseEntity.ok(ticketRepository.findTicketsByHandlerId(0));
    }

    @PostMapping("/ticket/update/status")
    public ResponseEntity<String> updateTicketStatus(@RequestParam("ticketId") int ticketId, @RequestParam("newStatus") boolean newStatus) {
        try {
            int rowsAffected = ticketRepository.updateStatus(ticketId, newStatus);
            if (rowsAffected > 0) {
                return new ResponseEntity<>("Ticket status updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Ticket not found or status not updated", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating ticket status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ticket/update/handler")
    public ResponseEntity<String> updateTicketHandler(@RequestBody Map<String, Integer> payload) {
        int ticketId = payload.get("ticketId");
        int newHandlerId = payload.get("newHandlerId");
        System.out.println("Received Payload: " + payload);
        try {
            int rowsAffected = ticketRepository.updateHandlerId(ticketId, newHandlerId);
            if (rowsAffected > 0) {
                return new ResponseEntity<>("Ticket handler updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Ticket not found or handler not updated", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating ticket handler", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/messages-by-ticket/{ticketId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByTicketIdOrderByDate(@PathVariable int ticketId) {
        try {
            List<MessageDTO> messages = messageRepository.getMessagesByTicketIdOrderByDate(ticketId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
