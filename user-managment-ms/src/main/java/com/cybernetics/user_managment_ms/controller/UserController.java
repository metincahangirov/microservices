package com.cybernetics.user_managment_ms.controller;

import com.cybernetics.user_managment_ms.dto.SuccessDto;
import com.cybernetics.user_managment_ms.dto.req.UserRequestDto;
import com.cybernetics.user_managment_ms.dto.req.UserUpdateRequestDto;
import com.cybernetics.user_managment_ms.dto.res.UserResponseDto;
import com.cybernetics.user_managment_ms.dto.res.UserUpdateResponseDto;
import com.cybernetics.user_managment_ms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cybernetics.user_managment_ms.utils.Status.SUCCESS;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<SuccessDto<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {

        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        SuccessDto<UserResponseDto> successDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);

    }

    @GetMapping("/all-users")
    public ResponseEntity<SuccessDto<List<UserResponseDto>>> getAllUsers() {

        List<UserResponseDto> userResponseDto = userService.getAllUsers();
        SuccessDto<List<UserResponseDto>> successDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);

    }

    @GetMapping("/users")
    public ResponseEntity<SuccessDto<UserResponseDto>> getUsers(String userName) {

        UserResponseDto userResponseDto = userService.getUsers(userName);
        SuccessDto<UserResponseDto> successDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);

    }


    @PutMapping("/users/{userName}")
    public ResponseEntity<SuccessDto<UserUpdateResponseDto>> updateUser(@PathVariable String userName,
                                                                         @RequestBody UserUpdateRequestDto userRequestDto) {

        UserUpdateResponseDto userResponseDto = userService.updateUsers(userRequestDto, userName);
        SuccessDto<UserUpdateResponseDto> successDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);

    }


    @DeleteMapping("/users/{userName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers(@PathVariable String userName) {
        userService.deleteUsers(userName);
    }


}
