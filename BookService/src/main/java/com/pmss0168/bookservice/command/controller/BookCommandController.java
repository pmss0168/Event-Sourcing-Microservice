package com.pmss0168.bookservice.command.controller;

import com.pmss0168.bookservice.command.command.CreateBookCommand;
import com.pmss0168.bookservice.command.command.DeleteBookCommand;
import com.pmss0168.bookservice.command.command.UpdateBookCommand;
import com.pmss0168.bookservice.command.model.BookRequestModel;
import com.pmss0168.commonservice.service.KafkaService;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    public String createBook(@Valid @RequestBody BookRequestModel request) {
        CreateBookCommand command = CreateBookCommand.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .author(request.getAuthor())
                .isReleased(Boolean.TRUE)
                .build();
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{bookId}")
    public String updateBook(@PathVariable String bookId, @RequestBody BookRequestModel request) {
        UpdateBookCommand command = UpdateBookCommand.builder()
                .id(bookId)
                .name(request.getName())
                .author(request.getAuthor())
                .isReleased(request.getIsReleased())
                .build();
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable String bookId) {
        DeleteBookCommand command = DeleteBookCommand.builder()
                .id(bookId)
                .build();
        return commandGateway.sendAndWait(command);
    }

    //Test Kafka
    @PostMapping("/kafka/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody String request) {
        kafkaService.sendMessage("test", request);
        return ResponseEntity.ok("Message sent: " + request);
    }

}
