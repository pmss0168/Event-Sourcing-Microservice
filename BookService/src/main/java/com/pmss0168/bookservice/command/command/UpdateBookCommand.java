package com.pmss0168.bookservice.command.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookCommand {
    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isReleased;
}
