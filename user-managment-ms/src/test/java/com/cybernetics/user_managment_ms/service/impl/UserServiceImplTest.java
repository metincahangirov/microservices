package com.cybernetics.user_managment_ms.service.impl;

import com.cybernetics.user_managment_ms.config.PasswordConfig;
import com.cybernetics.user_managment_ms.dto.req.UserRequestDto;
import com.cybernetics.user_managment_ms.dto.res.UserResponseDto;
import com.cybernetics.user_managment_ms.entity.UserEntity;
import com.cybernetics.user_managment_ms.mapper.UserMapper;
import com.cybernetics.user_managment_ms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserRepository.class,
        UserMapper.class,
        PasswordConfig.class, UserServiceImpl.class
})
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;
    @MockBean
    UserMapper userMapper;
    @Spy
    PasswordConfig passwordConfig;

    @Autowired
    UserServiceImpl userService;

    UserRequestDto userRequestDto;

    UserEntity userEntity;

    UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {

        userRequestDto = MockData.userRequestDto();
        userEntity = MockData.userEntity();
        userResponseDto = MockData.userResponseDto();
    }

    @Test
    void testWhenUserIsCreated() {

        when(userMapper.mapRequestDtoToEntity(userRequestDto)).thenReturn(userEntity);
        when(userMapper.mapUserResponseToEntity(userEntity)).thenReturn(userResponseDto);


        userService.createUser(userRequestDto);


        verify(userMapper).mapRequestDtoToEntity(userRequestDto);
        verify(userRepository,times(2)).save(userEntity);
        verify(userMapper).mapUserResponseToEntity(userEntity);


    }

}