package com.pmss0168.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestModel {
    private String id;

    @NotBlank(message = "Tên là bắt buộc")
    @Size(max = 50, message = "Tên phải tối đa 50 ký tự")
    private String name;
    @NotBlank(message = "Tên tác giả là bắt buộc")
    @Size(max = 50, message = "Tên tác giả phải tối đa 50 ký tự")
    private String author;

    private Boolean isReleased;
}
