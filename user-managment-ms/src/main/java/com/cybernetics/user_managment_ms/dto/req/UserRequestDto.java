package com.cybernetics.user_managment_ms.dto.req;

import com.cybernetics.user_managment_ms.utils.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserRequestDto(@NotBlank(message = "name boş ola bilməz!") String reqName,
                             String surname,
                             @NotBlank(message = "userName boş ola bilməz!")
                             String userName,
                             String email,
                             @NotBlank(message = "password boş ola bilməz!")
                             String password,
                             UserRole role,
                             String phoneNumber,
                             Instant birthDate

) {
}
