package com.pmss0168.borrowingservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowingCommand {
    @TargetAggregateIdentifier
    private String id;

    private String bookId;

    private String employeeId;

    private Date borrowingDate;
}
