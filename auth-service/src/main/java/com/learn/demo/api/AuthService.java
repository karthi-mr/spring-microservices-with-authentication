package com.learn.demo.api;

import com.learn.demo.User.User;
import com.learn.demo.User.UserRepository;
import com.learn.demo.api.dto.LoginRequest;
import com.learn.demo.api.dto.LoginResponse;
import com.learn.demo.api.dto.RegistrationRequest;
import com.learn.demo.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequest request) {
        if (userRepository.existsUserByEmail(request.email())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles("ROLE_USER")
                .enabled(true)
                .build();
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String token = jwtService.generateToken(user.getUsername(), List.of(user.getRoles()));
        return new LoginResponse(token);
    }
}
