package com.Rishabh.Order_Service.Utils;

import com.Rishabh.Order_Service.Entity.PrimaryDb.Chef;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChefDataStore {

    private final Map<Long, Chef> chefs = new HashMap<>();

    @PostConstruct
    public void loadChefs(){
        chefs.put(1L,new Chef(1L, "Chef Rahul","Italian",500.0,false));
        chefs.put(2L,new Chef(2L, "Chef Rishabh","Bhang Bhosda",600.0,true));
        chefs.put(3L,new Chef(3L, "Chef Lakshit","Kutte ka Lund",700.0,true));
    }

    public Chef getChef(Long chefId){
        return chefs.get(chefId);
    }

//    public Chef getChefName(Long chefId){
//        return chefs.
//    }

    public List<Chef> getAllChefs(){
        return new ArrayList<>(chefs.values());
    }
}
