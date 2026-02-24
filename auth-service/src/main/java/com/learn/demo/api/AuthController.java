package com.learn.demo.api;

import com.learn.demo.api.dto.LoginRequest;
import com.learn.demo.api.dto.LoginResponse;
import com.learn.demo.api.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        return ResponseEntity
                .ok(authService.login(request));
    }
}
