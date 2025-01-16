package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.MessageRequest;
import com.openclassrooms.chatop.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api")
public class MessageController {

    MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Map<String, String>> saveMessage(@Valid @RequestBody MessageRequest message){
        if(messageService.saveMessage(message) != null) {
            return ResponseEntity.ok(Map.of("message", "Message send with success"));
        }
        else {
            return ResponseEntity.ok(Map.of("error", "Message not send with success"));
        }

    }


}
