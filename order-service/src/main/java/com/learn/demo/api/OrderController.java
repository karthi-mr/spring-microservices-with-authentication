package com.learn.demo.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> me(Authentication authentication) {
        return ResponseEntity
                .ok(format("Hello %s, you can access USER endpoints.", authentication.getName()));
    }

    @GetMapping("/admin/secret")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> adminOnly(Authentication authentication) {
        return ResponseEntity
                .ok(format("Hello %s, ADMIN secret data.", authentication.getName()));
    }
}
