package com.learn.demo.api.dto;

public record LoginRequest(
        String email,

        String password
) {
}
