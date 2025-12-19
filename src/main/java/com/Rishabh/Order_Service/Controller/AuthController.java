package com.Rishabh.Order_Service.Controller;

import com.Rishabh.Order_Service.DTO.AuthRequest;
import com.Rishabh.Order_Service.DTO.AuthResponse;
import com.Rishabh.Order_Service.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping
    public AuthResponse login(@RequestBody AuthRequest authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(),
                authRequest.getPassword()
        ));

        UserDetails user = userDetailsService.loadUserByUsername(authRequest.getUserName());

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }



}
