package com.Rishabh.Order_Service.Service;

import com.Rishabh.Order_Service.DTO.ChefBookedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingEventProducerService {

    private static final String TOPIC = "chef-booked";

    @Autowired
    private KafkaTemplate<String, ChefBookedEvent> kafkaTemplate;

    public void sendChefBookedEvent(ChefBookedEvent event){
        kafkaTemplate.send(TOPIC,event);
    }
}
