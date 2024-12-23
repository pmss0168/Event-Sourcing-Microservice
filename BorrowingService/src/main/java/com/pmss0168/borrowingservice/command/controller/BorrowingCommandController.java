package com.pmss0168.borrowingservice.command.controller;

import com.pmss0168.borrowingservice.command.command.CreateBorrowingCommand;
import com.pmss0168.borrowingservice.command.model.BorrowingCreateRequestModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createBorrowing(@Valid @RequestBody BorrowingCreateRequestModel request){
        CreateBorrowingCommand command = CreateBorrowingCommand.builder()
                .id(UUID.randomUUID().toString())
                .bookId(request.getBookId())
                .employeeId(request.getEmployeeId())
                .borrowingDate(new Date())
                .build();
        return commandGateway.sendAndWait(command);
    }

}
