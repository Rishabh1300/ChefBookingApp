package com.Rishabh.Order_Service.Entity.PrimaryDb;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long chefId;

    private LocalDate bookingDate;
    private LocalTime startTime;
    private Integer durationInHours;

    private String address;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    private String specialInstructions;
    private Double price;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = BookingStatus.CREATED;
    }

    @PreUpdate
    void onUpdate(){
        updatedAt = LocalDateTime.now();
    }






}
