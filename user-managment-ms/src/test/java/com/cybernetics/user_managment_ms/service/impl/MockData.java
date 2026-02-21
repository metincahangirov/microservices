package com.cybernetics.user_managment_ms.service.impl;

import com.cybernetics.user_managment_ms.dto.req.UserRequestDto;
import com.cybernetics.user_managment_ms.dto.res.UserResponseDto;
import com.cybernetics.user_managment_ms.entity.UserEntity;
import com.cybernetics.user_managment_ms.utils.UserRole;

public class MockData {

    public static UserRequestDto userRequestDto(){
        return UserRequestDto.builder()
                .role(UserRole.USER)
                .reqName("test")
                .password("1234567898765")
                .surname("test")
                .build();
    }

    public static UserEntity userEntity(){
        return UserEntity.builder()
                .name("test")
                .surname("test")
                .role(UserRole.USER)
                .build();
    }

    public static UserResponseDto userResponseDto(){
        return UserResponseDto.builder()
                .email("test")
                .userName("test")
                .build();
    }

}
