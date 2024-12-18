package com.pmss0168.bookservice.command.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateEvent {
    private String id;

    private String name;

    private String author;

    private Boolean isReleased;
}
