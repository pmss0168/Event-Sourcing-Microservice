package com.pmss0168.commonservice.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private Integer code;

    private String message;

    private HttpStatus statusCode;
}
