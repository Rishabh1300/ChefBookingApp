package com.Rishabh.Order_Service.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ApiError {

    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
