package com.cybernetics.user_managment_ms.dto;

import com.cybernetics.user_managment_ms.utils.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessDto <T>{

    Status status;
    T data;

    public SuccessDto(Status status, T data) {
        this.status = status;
        this.data = data;
    }
}
