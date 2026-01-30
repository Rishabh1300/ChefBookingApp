package com.Rishabh.Order_Service.Service;

import com.Rishabh.Order_Service.DTO.BookingRequest;
import com.Rishabh.Order_Service.DTO.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);

    List<BookingResponse> getMyBookings(Long id);

    List<BookingResponse> getChefBookings(Long chefId);

    List<BookingResponse> getAllBookings();

    public void deleteUserBooking(Long userId, Long bookingId);

    public void deleteChefBooking(Long userId,Long bookingId);

    //for admin
    public void deleteBooking(Long bookingId);

    public void confirmBooking(Long bookingId);

}
