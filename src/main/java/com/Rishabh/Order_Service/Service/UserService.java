package com.Rishabh.Order_Service.Service;
import com.Rishabh.Order_Service.Config.SecurityConfig;
import com.Rishabh.Order_Service.DTO.UserRequest;
import com.Rishabh.Order_Service.DTO.UserResponse;
import com.Rishabh.Order_Service.Entity.PrimaryDb.User;
import com.Rishabh.Order_Service.Exceptions.UserNotFoundException;
import com.Rishabh.Order_Service.Mapper.UserMapper;
import com.Rishabh.Order_Service.Repository.PrimaryRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toUserResponse).toList();
    }

    public User userIsAvailable(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("This user does not exists "));
    }


        public void createUser(UserRequest userRequest){
            User user = User.builder()
                    .userName(userRequest.getUserName())
                    .password(securityConfig.passwordEncoder().encode(userRequest.getPassword()))
                    .build();

            userRepository.save(user);
            log.info("Product {} is save",user.getUsername());
        }
}
