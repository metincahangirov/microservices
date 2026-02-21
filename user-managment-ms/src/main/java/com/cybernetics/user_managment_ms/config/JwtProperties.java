package com.cybernetics.user_managment_ms.config;

import com.cybernetics.user_managment_ms.dto.AccessTokenProperties;
import com.cybernetics.user_managment_ms.dto.RefreshTokenProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(@NotNull
                            @Valid
                            AccessTokenProperties accessToken,

                            @NotNull
                            @Valid
                            RefreshTokenProperties refreshToken) {
}
