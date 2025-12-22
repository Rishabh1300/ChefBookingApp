package com.Rishabh.Order_Service.Controller;

import com.Rishabh.Order_Service.DTO.BookingRequest;
import com.Rishabh.Order_Service.DTO.BookingResponse;
import com.Rishabh.Order_Service.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingRequest request){
        return bookingService.createBooking(request);
    }


    @GetMapping("/user/{userId}")
    public List<BookingResponse> getMappingFromUserId(@PathVariable Long userId){
        return bookingService.getMyBookings(userId);
    }


    @GetMapping("/chef/{chefId}")
    public List<BookingResponse> getMappingFromChefId(@PathVariable Long chefId){
        return bookingService.getMyBookings(chefId);
    }

    @GetMapping
    public List<BookingResponse> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @DeleteMapping("/chef/{chefId}/{bookingId}")
    public void deleteBookingByChefId(@PathVariable Long chefId,@PathVariable Long bookingId){
        bookingService.deleteChefBooking(chefId, bookingId);
    }

    @DeleteMapping("/user/{userId}/{bookingId}")
    public void deleteBookingByUserId(@PathVariable Long userId,@PathVariable Long bookingId){
        bookingService.deleteUserBooking(userId,bookingId);
    }

    //just for admin panel
    @DeleteMapping("/{bookingId}")
    public void deleteBookingForAdmin(@PathVariable Long bookingId){
        bookingService.deleteBooking(bookingId);
    }
}
