package com.Rishabh.Order_Service.Repository.PrimaryRepository;

import com.Rishabh.Order_Service.Entity.PrimaryDb.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByUserId(Long userId);
    List<Booking> findByChefId(Long chefId);
    void deleteByUserId(Long userId);
    void deleteByChefId(Long chefId);

}
