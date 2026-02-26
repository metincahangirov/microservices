package com.cybernetics.user_managment_ms.service.impl;

import com.cybernetics.user_managment_ms.config.PasswordConfig;
import com.cybernetics.user_managment_ms.dto.req.UserRequestDto;
import com.cybernetics.user_managment_ms.dto.req.UserUpdateRequestDto;
import com.cybernetics.user_managment_ms.dto.res.UserResponseDto;
import com.cybernetics.user_managment_ms.dto.res.UserUpdateResponseDto;
import com.cybernetics.user_managment_ms.entity.UserEntity;
import com.cybernetics.user_managment_ms.exception.UserNotFoundException;
import com.cybernetics.user_managment_ms.mapper.UserMapper;
import com.cybernetics.user_managment_ms.repository.UserRepository;
import com.cybernetics.user_managment_ms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordConfig passwordEncoderConfig;


    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = userMapper.mapRequestDtoToEntity(userRequestDto);
        userEntity.setPassword(passwordEncoderConfig.passwordEncoder()
                .encode(userRequestDto.password()));
        userRepository.save(userEntity);
        userRepository.save(userEntity);

        return userMapper.mapUserResponseToEntity(userEntity);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        return userMapper.mapEntityListToResponseList(userEntities);
    }

    @Override
    public UserResponseDto getUsers(String userName) {

        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        return userMapper.mapUserResponseToEntity(userEntity);
    }

    @Override
    public UserUpdateResponseDto updateUsers(UserUpdateRequestDto userRequestDto, String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity == null) {
            throw new UserNotFoundException("User not found");

        }
        userEntity.setName(userRequestDto.name());
        userEntity.setSurname(userRequestDto.surname());
        userEntity.setEmail(userRequestDto.email());
        userEntity.setPassword(userRequestDto.password());
        userEntity.setRole(userRequestDto.role());
        userEntity.setBirthDate(userRequestDto.birthDate());
        userEntity.setUpdatedAt(LocalDateTime.now());


        userRepository.save(userEntity);


        return UserUpdateResponseDto.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .phoneNumber(userEntity.getPhoneNumber())
                .email(userEntity.getEmail())
                .birthDate(userEntity.getBirthDate())
                .role(userEntity.getRole())
                .build();
    }

    @Transactional
    @Override
    public void deleteUsers(String userName) {
        UserEntity user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteByUserName(userName);


    }


}