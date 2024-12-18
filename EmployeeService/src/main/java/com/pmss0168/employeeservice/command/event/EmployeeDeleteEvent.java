package com.pmss0168.employeeservice.command.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDeleteEvent {
    private String id;
}
