package com.Rishabh.Order_Service.Repository;

import com.Rishabh.Order_Service.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByUserId(Long userId);
    List<Booking> findByChefId(Long chefId);
    void deleteByUserId(Long userId);
    void deleteByChefId(Long chefId);

}
