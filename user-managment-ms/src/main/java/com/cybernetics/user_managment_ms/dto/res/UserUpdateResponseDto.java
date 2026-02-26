package com.cybernetics.user_managment_ms.dto.res;

import com.cybernetics.user_managment_ms.utils.UserRole;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserUpdateResponseDto(String name,
                                    String surname,
                                    String userName,
                                    String email,
                                    String password,
                                    UserRole role,
                                    String phoneNumber,
                                    Instant birthDate) {
}
