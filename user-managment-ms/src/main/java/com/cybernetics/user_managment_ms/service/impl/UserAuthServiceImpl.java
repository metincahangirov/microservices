package com.cybernetics.user_managment_ms.service.impl;

import com.cybernetics.user_managment_ms.config.PasswordConfig;
import com.cybernetics.user_managment_ms.dto.req.LoginRequest;
import com.cybernetics.user_managment_ms.dto.res.AuthResponse;
import com.cybernetics.user_managment_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_managment_ms.entity.UserEntity;
import com.cybernetics.user_managment_ms.exception.WrongPasswordException;
import com.cybernetics.user_managment_ms.repository.UserRepository;
import com.cybernetics.user_managment_ms.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final PasswordConfig passwordEncoderConfig;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        UserEntity userEntity = userRepository.findByUserName(loginRequest.userName());

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        boolean isValid = passwordEncoderConfig.passwordEncoder().matches(loginRequest.password(), userEntity.getPassword());

        if (!isValid) {
            throw new WrongPasswordException("Invalid password");
        }


        String accessToken = jwtService.generateAccessToken(userEntity);
        RefreshTokenEntity refreshToken = jwtService.createRefreshToken(userEntity);


        return new AuthResponse(
                accessToken,
                refreshToken.getToken());
    }
}
