package com.Rishabh.Order_Service.Mapper;

import com.Rishabh.Order_Service.DTO.UserResponse;
import com.Rishabh.Order_Service.Entity.User;


public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .build();
    }





}
