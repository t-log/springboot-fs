package com.tlog.model;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {}
