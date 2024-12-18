package com.pmss0168.employeeservice.command.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String Kin;

    @Builder.Default
    private Boolean isDiscipline = false;
}
