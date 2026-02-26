package com.cybernetics.payment_ms.dto;

import com.cybernetics.payment_ms.utils.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SuccessDto<T> {

    private Status status;
    private T data;

    public SuccessDto(Status status, T data) {
        this.status = status;
        this.data = data;
    }

}
