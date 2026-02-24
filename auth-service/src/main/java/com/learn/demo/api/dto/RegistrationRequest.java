package com.learn.demo.api.dto;

public record RegistrationRequest(
        String email,

        String password
) {
}
