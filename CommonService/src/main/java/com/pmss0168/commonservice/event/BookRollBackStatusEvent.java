package com.pmss0168.commonservice.event;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRollBackStatusEvent {
    @TargetAggregateIdentifier
    private String bookId;

    private Boolean isReleased;

    private String employeeId;

    private String borrowingId;
}
