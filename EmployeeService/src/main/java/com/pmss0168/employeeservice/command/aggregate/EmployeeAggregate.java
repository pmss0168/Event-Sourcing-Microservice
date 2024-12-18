package com.pmss0168.employeeservice.command.aggregate;

import com.pmss0168.employeeservice.command.command.CreateEmployeeCommand;
import com.pmss0168.employeeservice.command.command.DeleteEmployeeCommand;
import com.pmss0168.employeeservice.command.command.UpdateEmployeeCommand;
import com.pmss0168.employeeservice.command.event.EmployeeCreateEvent;
import com.pmss0168.employeeservice.command.event.EmployeeDeleteEvent;
import com.pmss0168.employeeservice.command.event.EmployeeUpdateEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Aggregate
@Getter
@Setter
@NoArgsConstructor
public class EmployeeAggregate {
    @AggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String Kin;
    private Boolean isDiscipline;

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command) {
        EmployeeCreateEvent event = new EmployeeCreateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateEmployeeCommand command){
        EmployeeUpdateEvent event = new EmployeeUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteEmployeeCommand command){
        EmployeeDeleteEvent event = new EmployeeDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeCreateEvent event) {
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.Kin = event.getKin();
        this.isDiscipline = event.getIsDiscipline();
    }

    @EventSourcingHandler
    public void on(EmployeeUpdateEvent event) {
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.Kin = event.getKin();
        this.isDiscipline = event.getIsDiscipline();
    }

    @EventSourcingHandler
    public void on(EmployeeDeleteEvent event) {
        this.id = event.getId();
    }
}
