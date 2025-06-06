package com.browna.teller_back.controllers;

import com.browna.teller_back.config.CookieProperties;
import com.browna.teller_back.payload.AuthenticationRequest;
import com.browna.teller_back.payload.AuthenticationResponse;
import com.browna.teller_back.payload.RegisterRequest;
import com.browna.teller_back.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final CookieProperties cookieProperties;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse payload = service.authenticate(request);

        String cookieHeader = String.format(
                "jwtCookie=%s; Domain=%s; Max-Age=3600; HttpOnly; Path=/; SameSite=%s; %s",
                payload.getToken(),
                cookieProperties.getDomain(),
                cookieProperties.getSameSite(),
                cookieProperties.isSecure() ? "Secure;" : ""
        );
        response.addHeader("Set-Cookie", cookieHeader);

        return ResponseEntity.ok(payload);
    }
}
