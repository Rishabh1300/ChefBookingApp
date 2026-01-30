package com.Rishabh.Order_Service.DTO;

import com.Rishabh.Order_Service.Entity.PrimaryDb.ServiceType;
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
public class BookingRequest {


    private Long userId;
    private Long chefId;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private Integer durationInHours;

    private String address;
    private ServiceType serviceType;
    private String specialInstructions;

}


