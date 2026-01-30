package com.Rishabh.Order_Service.Service.BookingServiceImpl;


import com.Rishabh.Order_Service.Annotation.LogExecutionTime;
import com.Rishabh.Order_Service.DTO.*;
import com.Rishabh.Order_Service.Entity.PrimaryDb.Booking;
import com.Rishabh.Order_Service.Entity.PrimaryDb.BookingStatus;
import com.Rishabh.Order_Service.Entity.PrimaryDb.Chef;
import com.Rishabh.Order_Service.Entity.PrimaryDb.User;
import com.Rishabh.Order_Service.Exceptions.BookingNotFoundException;
import com.Rishabh.Order_Service.Exceptions.UserNotFoundException;
import com.Rishabh.Order_Service.Mapper.BookingMapper;
import com.Rishabh.Order_Service.Repository.PrimaryRepository.BookingRepository;
import com.Rishabh.Order_Service.Repository.PrimaryRepository.UserRepository;
import com.Rishabh.Order_Service.Service.BookingEventProducerService;
import com.Rishabh.Order_Service.Service.BookingService;
import com.Rishabh.Order_Service.Service.ChefClient;
import com.Rishabh.Order_Service.Service.UserService;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    //    private final UserDetails userDetails;
    private final ChefClient chefClient;
    private final BookingEventProducerService bookingEventProducerService;


    //because user api is not there
//    private final UserRequest userRequest;


    @Override
    @LogExecutionTime
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Long userId = bookingRequest.getUserId();

        chefClient.validateAvailability(
                bookingRequest.getChefId(),
                bookingRequest.getBookingDate(),
                bookingRequest.getStartTime(),
                bookingRequest.getDurationInHours()
        );

       userService.userIsAvailable(bookingRequest.getUserId());

       Double price = chefClient.calculatePrice(bookingRequest.getChefId(), bookingRequest.getDurationInHours());

       User user = userRepository.findById(bookingRequest.getUserId()).orElseThrow(()-> new UserNotFoundException("User not available"));


        Booking booking = Booking.builder()
                .userId(bookingRequest.getUserId())
                .chefId(bookingRequest.getChefId())
                .bookingDate(bookingRequest.getBookingDate())
                .startTime(bookingRequest.getStartTime())
                .durationInHours(bookingRequest.getDurationInHours())
                .address(bookingRequest.getAddress())
                .serviceType(bookingRequest.getServiceType())
                .price(price)
                .specialInstructions(bookingRequest.getSpecialInstructions())
                .status(BookingStatus.CREATED)
                .build();
        bookingRepository.save(booking);

        ChefBookedEvent event = ChefBookedEvent.builder()
                .bookingId(booking.getId())
                .userEmail(user.getEmail())
                .chefId(bookingRequest.getChefId())
                .bookingDate(bookingRequest.getBookingDate())
                .startTime(booking.getStartTime())
                .durationInHours(booking.getDurationInHours())
                .build();

        bookingEventProducerService.sendChefBookedEvent(event);

        return BookingMapper.mapToResponse(booking);




    }


    @Override
    @Transactional
    public void confirmBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
        if(booking.getStatus()==BookingStatus.CREATED){
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }
    }

    @Override
    @Cacheable(
            value = "user-bookings",
            key = "#root.args[0]",
            unless = "#result==null || #result.isEmpty()"
    )
    public List<BookingResponse> getMyBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No Bookings found for user");
        }
        return bookingRepository.findByUserId(userId)
                .stream().map(BookingMapper::mapToResponse).toList();
    }


//    public Page<BookingResponse> getAllBooking(Pageable pageable){
//        Page<Booking> bookings = bookingRepository.findAll(pageable);
//    }
    @Override
    @Cacheable(
            value = "chef-bookings",
            key = "root.args[1]",
            unless = "#result==null || #result.isEmpty()"
    )
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

    @Override
    public void deleteBooking(Long bookingId){
        bookingRepository.deleteById(bookingId);
    }
}
