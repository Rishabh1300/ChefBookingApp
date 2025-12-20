package com.Rishabh.Order_Service.Service;

import com.Rishabh.Order_Service.Entity.SecondaryDb.TestEntity;
import com.Rishabh.Order_Service.Repository.SecondaryRepository.TestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepo testRepo;

    public TestEntity createUser(TestEntity testEntity){
        return testRepo.save(testEntity);
    }

    public List<TestEntity> getAllUser(){
        return testRepo.findAll();
    }
}


