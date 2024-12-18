package com.pmss0168.employeeservice.query.controller;

import com.pmss0168.employeeservice.query.model.EmployeeResponseModel;
import com.pmss0168.employeeservice.query.queries.GetAllEmployeeQuery;
import com.pmss0168.employeeservice.query.queries.GetEmployeeDetailQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    @Operation(
            summary = "Get All Employee",
            description = "Get all employee filter with isDiscipline"
    )
    public List<EmployeeResponseModel> getAllEmployee(@RequestParam(name = "isDiscipline", defaultValue = "false", required = false) Boolean isDiscipline) {
        log.info("Get All Employee");
        GetAllEmployeeQuery query = GetAllEmployeeQuery.builder()
                .isDiscipline(isDiscipline)
                .build();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    @Operation(
            summary = "Get Employee Detail",
            description = "Get employee detail"
    )
    public EmployeeResponseModel getEmployee(@PathVariable String employeeId) {
        log.info("Get Employee Detail");
        GetEmployeeDetailQuery query = GetEmployeeDetailQuery.builder()
                .id(employeeId)
                .build();
        return queryGateway.query(query, ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
    }
}
