package com.cybernetics.user_managment_ms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AccessTokenProperties(@NotBlank
                                    String secret,
                                    @Min(1)
                                    long expirationMinutes) {
}
