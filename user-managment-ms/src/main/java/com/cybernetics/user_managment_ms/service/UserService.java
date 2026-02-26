package com.cybernetics.user_managment_ms.service;

import com.cybernetics.user_managment_ms.dto.req.UserRequestDto;
import com.cybernetics.user_managment_ms.dto.req.UserUpdateRequestDto;
import com.cybernetics.user_managment_ms.dto.res.UserResponseDto;
import com.cybernetics.user_managment_ms.dto.res.UserUpdateResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUsers(String userName);


    UserUpdateResponseDto updateUsers(UserUpdateRequestDto userRequestDto, String userName);

    void deleteUsers(String userName);

}
