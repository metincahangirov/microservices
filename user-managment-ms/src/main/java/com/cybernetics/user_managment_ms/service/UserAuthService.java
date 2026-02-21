package com.cybernetics.user_managment_ms.service;

import com.cybernetics.user_managment_ms.dto.req.LoginRequest;
import com.cybernetics.user_managment_ms.dto.res.AuthResponse;

public interface UserAuthService {

    AuthResponse login(LoginRequest loginRequest);
}
