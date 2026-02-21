package com.cybernetics.user_managment_ms.dto;

import jakarta.validation.constraints.Min;

public record RefreshTokenProperties(@Min(1)
                                     long expirationDays) {
}
