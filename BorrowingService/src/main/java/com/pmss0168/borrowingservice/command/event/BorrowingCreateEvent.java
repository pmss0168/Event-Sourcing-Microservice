    package com.pmss0168.borrowingservice.command.event;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingCreateEvent {
    @TargetAggregateIdentifier
    private String id;

    private String bookId;

    private String employeeId;

    private Date borrowingDate;
}
