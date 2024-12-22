package com.pmss0168.employeeservice.command.controller;

import com.pmss0168.commonservice.service.KafkaService;
import com.pmss0168.employeeservice.command.command.CreateEmployeeCommand;
import com.pmss0168.employeeservice.command.command.DeleteEmployeeCommand;
import com.pmss0168.employeeservice.command.command.UpdateEmployeeCommand;
import com.pmss0168.employeeservice.command.data.Employee;
import com.pmss0168.employeeservice.command.model.CreateEmployeeRequestModel;
import com.pmss0168.employeeservice.command.model.UpdateEmployeeRequestModel;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    public String createEmployee(@Valid @RequestBody CreateEmployeeRequestModel request) {
        CreateEmployeeCommand command = CreateEmployeeCommand.builder()
                .id(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .Kin(request.getKin())
                .isDiscipline(Boolean.FALSE)
                .build();
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{employeeId}")
    public String updateEmployee(@PathVariable String employeeId, @Valid @RequestBody UpdateEmployeeRequestModel request) {
        UpdateEmployeeCommand command = UpdateEmployeeCommand.builder()
                .id(employeeId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .Kin(request.getKin())
                .isDiscipline(Boolean.valueOf(request.getIsDiscipline()))
                .build();
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{employeeId}")
    @Hidden
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = DeleteEmployeeCommand.builder()
                .id(employeeId)
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
