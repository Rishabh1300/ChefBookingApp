package com.Rishabh.Order_Service.Service.BookingServiceImpl;


import com.Rishabh.Order_Service.DTO.BookingRequest;
import com.Rishabh.Order_Service.DTO.BookingResponse;
import com.Rishabh.Order_Service.Entity.PrimaryDb.Booking;
import com.Rishabh.Order_Service.Entity.PrimaryDb.Chef;
import com.Rishabh.Order_Service.Exceptions.BookingNotFoundException;
import com.Rishabh.Order_Service.Mapper.BookingMapper;
import com.Rishabh.Order_Service.Repository.PrimaryRepository.BookingRepository;
import com.Rishabh.Order_Service.Service.BookingService;
import com.Rishabh.Order_Service.Service.ChefClient;
import com.Rishabh.Order_Service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    //    private final UserDetails userDetails;
    private final ChefClient chefClient;
    //because user api is not there
//    private final UserRequest userRequest;


    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Long userId = bookingRequest.getUserId();

        chefClient.validateAvailability(
                bookingRequest.getChefId(),
                bookingRequest.getBookingDate(),
                bookingRequest.getStartTime(),
                bookingRequest.getDurationInHours()
        );

       userService.userIsAvailable(bookingRequest.getUserId());


        Booking booking = Booking.builder()
                .userId(bookingRequest.getUserId())
                .chefId(bookingRequest.getChefId())
                .bookingDate(bookingRequest.getBookingDate())
                .startTime(bookingRequest.getStartTime())
                .durationInHours(bookingRequest.getDurationInHours())
                .address(bookingRequest.getAddress())
                .serviceType(bookingRequest.getServiceType())
                .price(chefClient.calculatePrice(bookingRequest.getChefId(), bookingRequest.getDurationInHours()))
                .specialInstructions(bookingRequest.getSpecialInstructions())
                .build();
        bookingRepository.save(booking);


        return BookingMapper.mapToResponse(booking);


    }

    @Override
    public List<BookingResponse> getMyBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No Bookings found for user");
        }
        return bookingRepository.findByUserId(userId)
                .stream().map(BookingMapper::mapToResponse).toList();
    }

    @Override
    public List<BookingResponse> getChefBookings(Long chefId) {
        Chef chef = chefClient.getChef(chefId);
        if (chef == null) {
            throw new BookingNotFoundException("No Bookings found for user");
        }
        return bookingRepository.findByChefId(chefId).stream().map(BookingMapper::mapToResponse).toList();
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> booking = bookingRepository.findAll();
        return booking.stream().map(BookingMapper::mapToResponse).toList();

    }

    @Override
    public void deleteUserBooking(Long userId, Long bookingId) {
        Optional<Booking> bookings = bookingRepository.findByUserId(userId).stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst();
        if(bookings.isPresent()){
            bookingRepository.deleteById(bookingId);
        }else{
            throw new BookingNotFoundException("Booking not found with the booking id -> "+bookingId);
        }

    }

    @Override
    public void deleteChefBooking(Long chefId, Long bookingId) {
        Optional<Booking> bookings = bookingRepository.findByChefId(chefId).stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst();

        if (bookings.isPresent()) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new BookingNotFoundException("Booking not found with the booking id -> " + bookingId);
        }

    }
}
