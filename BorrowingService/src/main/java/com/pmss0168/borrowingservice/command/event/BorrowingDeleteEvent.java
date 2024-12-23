package com.pmss0168.borrowingservice.command.event;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingDeleteEvent {
    private String id;
}
