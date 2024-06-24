package com.tlog.model;

public record CustomerUpdateRequest(String name,
                                    String email,
                                    Integer age) {
}
