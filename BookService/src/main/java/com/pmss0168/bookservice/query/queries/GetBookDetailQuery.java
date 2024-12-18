package com.pmss0168.bookservice.query.queries;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookDetailQuery {
    private String id;
}
