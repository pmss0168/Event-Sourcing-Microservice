package com.pmss0168.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequestModel {
    @NotBlank(message = "Họ không được để trống")
    private String firstName;
    @NotBlank(message = "Tên không được để trống")
    private String lastName;
    @NotBlank(message = "Kin không được để trống")
    private String Kin;
    @NotNull(message = "isDiscipline không được rỗng")
    private String isDiscipline;
}
