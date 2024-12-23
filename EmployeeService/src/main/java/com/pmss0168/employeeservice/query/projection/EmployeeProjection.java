package com.pmss0168.employeeservice.query.projection;

import com.pmss0168.commonservice.model.EmployeeResponseModel;
import com.pmss0168.commonservice.query.GetEmployeeDetailQuery;
import com.pmss0168.employeeservice.command.data.Employee;
import com.pmss0168.employeeservice.command.data.EmployeeRepository;
import com.pmss0168.employeeservice.query.queries.GetAllEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query){
        List<Employee> employees = employeeRepository.findAllByIsDiscipline(query.getIsDiscipline());
        return employees.stream().map(employee -> {
            EmployeeResponseModel response = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, response);
            return response;
        }).toList();
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeDetailQuery query){
        Employee employee = employeeRepository.findById(query.getId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        EmployeeResponseModel response = new EmployeeResponseModel();
        BeanUtils.copyProperties(employee, response);
        return response;
    }
}
