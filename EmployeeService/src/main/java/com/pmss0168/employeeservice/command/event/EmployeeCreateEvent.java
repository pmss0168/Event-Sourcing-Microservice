package com.pmss0168.employeeservice.command.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateEvent {
    private String id;
    private String firstName;
    private String lastName;
    private String Kin;
    private Boolean isDiscipline;
}
