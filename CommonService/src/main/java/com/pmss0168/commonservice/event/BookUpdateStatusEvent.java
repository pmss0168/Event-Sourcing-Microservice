package com.pmss0168.commonservice.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateStatusEvent {
    private String bookId;

    private Boolean isReleased;

    private String employeeId;

    private String borrowingId;
}
