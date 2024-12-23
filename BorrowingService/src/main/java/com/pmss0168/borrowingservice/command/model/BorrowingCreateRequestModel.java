package com.pmss0168.borrowingservice.command.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingCreateRequestModel {
    private String bookId;

    private String employeeId;
}
