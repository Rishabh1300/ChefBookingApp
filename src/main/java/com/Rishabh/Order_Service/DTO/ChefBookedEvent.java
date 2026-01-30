package com.Rishabh.Order_Service.DTO;

import com.Rishabh.Order_Service.Entity.PrimaryDb.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChefBookedEvent {

    private Long bookingId;
    private Long chefId;
    private String userEmail;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private Integer durationInHours;

}
