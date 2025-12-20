package com.Rishabh.Order_Service.Service;


import com.Rishabh.Order_Service.Entity.PrimaryDb.Chef;
import com.Rishabh.Order_Service.Exceptions.ChefNotAvailableException;
import com.Rishabh.Order_Service.Exceptions.ChefNotFoundException;
import com.Rishabh.Order_Service.Utils.ChefDataStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChefClient {

    private final ChefDataStore chefDataStore;

    public void validateAvailability(
            Long chefId, Object date, Object time, Integer hours
    ){
        Chef chef = chefDataStore.getChef(chefId);



        if(chef==null){
            throw new ChefNotFoundException("No chef is present with this chefId "+chefId);
        }
        if(!chef.getAvailable()){
            throw new ChefNotAvailableException(chef.getName()+ " is not available");
        }
    }

    public Double calculatePrice(Long chefId, Integer hours){
        Chef chef = chefDataStore.getChef(chefId);
        return chef.getHourlyRate()*hours;
    }


    public Chef getChef(Long chefId){
        return chefDataStore.getChef(chefId);
    }
}
