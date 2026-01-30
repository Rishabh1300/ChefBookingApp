package com.Rishabh.Order_Service.DTO;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long bookingId;
    private Long userId;
    private Double amount;

}
