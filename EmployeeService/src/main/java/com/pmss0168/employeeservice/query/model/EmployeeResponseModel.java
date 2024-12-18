package com.pmss0168.employeeservice.query.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseModel {
    private String id;

    private String firstName;

    private String lastName;

    private String Kin;

    private Boolean isDiscipline;
}
