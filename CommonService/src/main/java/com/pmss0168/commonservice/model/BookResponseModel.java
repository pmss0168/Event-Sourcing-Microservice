package com.pmss0168.commonservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseModel {
    private String id;

    private String name;

    private String author;

    private Boolean isReleased;
}
