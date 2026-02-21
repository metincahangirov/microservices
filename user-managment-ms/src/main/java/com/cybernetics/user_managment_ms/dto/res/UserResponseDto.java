package com.cybernetics.user_managment_ms.dto.res;

import lombok.Builder;

@Builder
public record UserResponseDto(
        String userName,
        String email
) {
}
