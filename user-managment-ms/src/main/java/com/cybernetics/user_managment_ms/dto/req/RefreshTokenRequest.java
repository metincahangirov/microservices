package com.cybernetics.user_managment_ms.dto.req;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank
                                  String refreshToken) {
}
