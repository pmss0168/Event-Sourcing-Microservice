package com.pmss0168.borrowingservice.command.aggregate;

import com.pmss0168.borrowingservice.command.command.CreateBorrowingCommand;
import lombok.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingAggregate {
    @AggregateIdentifier
    private String id;

    private String bookId;

    private String employeeId;

    private Date borrowingDate;

    private Date returnDate;

    @CommandHandler
    public BorrowingAggregate(CreateBorrowingCommand command) {
        
    }
}
