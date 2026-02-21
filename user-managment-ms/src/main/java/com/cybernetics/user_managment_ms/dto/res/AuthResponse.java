package com.cybernetics.user_managment_ms.dto.res;

public record AuthResponse(String accessToken,
                           String refreshToken) {
}
