package com.pmss0168.commonservice.model;

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
