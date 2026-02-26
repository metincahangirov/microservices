package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.req.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.res.AzericardResponseDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;

import java.math.BigDecimal;

import static com.cybernetics.payment_ms.utils.PaymentStatus.NEW;

public class MockData {

    public static PaymentRequestDto paymentRequestDto() {
        return PaymentRequestDto.builder()
                .price(BigDecimal.valueOf(200))
                .productId("productId")
                .quantity(2)
                .build();
    }

    public static PaymentRequestDto paymentReqDtoQuantityIsBiggest() {
        return PaymentRequestDto.builder()
                .price(BigDecimal.valueOf(200))
                .productId("productId")
                .quantity(20)
                .build();
    }

    public static PaymentHistoryEntity paymentHistoryEntity() {
        return PaymentHistoryEntity.builder()
                .id("id")
                .azericardStatus("azericardStatus")
                .status(NEW.name())
                .price(200L)
                .productId("productId")
                .quantity(2)
                .build();
    }

//    public static PaymentHistoryEntity paymentHistoryEntityQuantityIsBiggest() {
//        return PaymentHistoryEntity.builder()
//                .id("id")
//                .azericardStatus("azericardStatus")
//                .status(NEW.name())
//                .price(200L)
//                .productId("productId")
//                .quantity(2)
//                .build();
//    }




    public static AzericardResponseDto azericardResponseDto() {
        return AzericardResponseDto.builder()
                .status("1")
                .orderId("orderId")
                .build();
    }

    public static AzericardResponseDto azericardResponseDtoFail() {
        return AzericardResponseDto.builder()
                .status("5")
                .orderId("orderId")
                .build();
    }
}
