package com.Rishabh.Order_Service.Mapper;

import com.Rishabh.Order_Service.DTO.BookingResponse;
import com.Rishabh.Order_Service.Entity.PrimaryDb.Booking;

public class BookingMapper {

    public static BookingResponse mapToResponse(Booking booking){
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .chefId(booking.getChefId())
                .status(booking.getStatus())
                .price(booking.getPrice())
                .bookingDate(booking.getBookingDate())
                .startTime(booking.getStartTime())
                .durationInHours(booking.getDurationInHours())
                .build();
    }
}
