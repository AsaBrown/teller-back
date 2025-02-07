package com.browna.teller_back.services;

import com.browna.teller_back.models.User;
import com.browna.teller_back.payload.AuthenticationRequest;
import com.browna.teller_back.payload.AuthenticationResponse;
import com.browna.teller_back.payload.RegisterRequest;
import com.browna.teller_back.repositories.UserRepository;
import com.browna.teller_back.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("Trying to authenticate");
        System.out.println(request.getEmail() + " " + request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
            )
        );
        System.out.println("Authenticated");
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("User found");
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
