package com.pmss0168.employeeservice.command.event;


import com.pmss0168.employeeservice.command.data.Employee;
import com.pmss0168.employeeservice.command.data.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;

@Slf4j
@Component
public class EmployeeEventHandler {
    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreateEvent event) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdateEvent event) {
        Employee employee = employeeRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDiscipline(event.getIsDiscipline());
        employeeRepository.save(employee);
    }

    @EventHandler
    @DisallowReplay
    public void on(EmployeeDeleteEvent event) {
        try {
            Employee employee = employeeRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Employee not found"));
            employeeRepository.deleteById(event.getId());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }
}
