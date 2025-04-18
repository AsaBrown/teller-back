package com.browna.teller_back.controllers;

import com.browna.teller_back.models.User;
import com.browna.teller_back.payload.UserInfoResponse;
import com.browna.teller_back.repositories.UserRepository;
import com.browna.teller_back.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserInfoController {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(HttpServletRequest request) {
        Optional<User> user = userRepository.findByUsername(jwtService.extractUserNameFromCookie(request));
        return UserInfoResponse.builder().user(user).build();
    }
}
