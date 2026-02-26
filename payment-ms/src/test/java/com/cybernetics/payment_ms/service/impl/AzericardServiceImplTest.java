package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.req.PaymentRequestDto;
import com.cybernetics.payment_ms.service.AzericardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class AzericardServiceImplTest {

    @Mock
    AzericardService azericardService;


    PaymentRequestDto paymentRequestDto;

    @BeforeEach
    void setUp() {

        paymentRequestDto = PaymentRequestDto.builder()
                .quantity(2)
                .price(BigDecimal.valueOf(200))
                .productId("productId")
                .build();
    }

    @Test
    void whenSuccess() {
        azericardService.payAzericard(paymentRequestDto);

        Assertions.assertEquals(paymentRequestDto.price(), BigDecimal.valueOf(200));
    }


}