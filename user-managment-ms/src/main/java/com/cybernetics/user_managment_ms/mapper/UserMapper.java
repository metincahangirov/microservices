package com.cybernetics.user_managment_ms.mapper;

import com.cybernetics.user_managment_ms.dto.req.UserRequestDto;
import com.cybernetics.user_managment_ms.dto.res.UserResponseDto;
import com.cybernetics.user_managment_ms.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", source = "reqName")
    UserEntity mapRequestDtoToEntity(UserRequestDto userRequestDto);

    UserResponseDto mapUserResponseToEntity(UserEntity userEntity);

    List<UserResponseDto> mapEntityListToResponseList(List<UserEntity> userEntities);

}
