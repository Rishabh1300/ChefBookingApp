package com.Rishabh.Order_Service.DTO;

import com.Rishabh.Order_Service.Entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long chefId;
    private BookingStatus status;
    private Double price;

    private LocalDate bookingDate;
    private LocalTime startTime;
    private Integer durationInHours;
}
