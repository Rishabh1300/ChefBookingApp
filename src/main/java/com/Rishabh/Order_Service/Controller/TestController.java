package com.Rishabh.Order_Service.Controller;

import com.Rishabh.Order_Service.Entity.SecondaryDb.TestEntity;
import com.Rishabh.Order_Service.Service.TestService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Test;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;


    @GetMapping
    public List<TestEntity> getAllUsers(){
        return testService.getAllUser();
    }

    @PostMapping
    public TestEntity createUser(@RequestBody TestEntity testEntity){
        return testService.createUser(testEntity);
    }

}
