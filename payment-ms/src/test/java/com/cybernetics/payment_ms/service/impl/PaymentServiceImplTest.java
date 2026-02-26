package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.req.KafkaReqDto;
import com.cybernetics.payment_ms.dto.req.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.res.AzericardResponseDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import com.cybernetics.payment_ms.feign.ProductFeignClient;
import com.cybernetics.payment_ms.mapper.PaymentHistoryMapper;
import com.cybernetics.payment_ms.repository.PaymentHistoryRepository;
import com.cybernetics.payment_ms.service.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        PaymentServiceImpl.class

})
class PaymentServiceImplTest {

    @MockitoBean
    AzericardServiceImpl azericardService;
    @MockitoBean
    ProductFeignClient productFeignClient;
    @MockitoBean
    PaymentHistoryRepository paymentHistoryRepository;
    @MockitoBean
    PaymentHistoryMapper paymentHistoryMapper;
    @MockitoBean
    KafkaTemplate<String, KafkaReqDto> kafkaTemplate;

    @MockitoBean
    SecurityContext securityContext;

    @MockitoBean
    Authentication authentication;

    @Autowired
    PaymentService paymentService;


    PaymentRequestDto paymentRequestDto;
    PaymentHistoryEntity paymentHistoryEntity;
    AzericardResponseDto azericardResponseDto;
    PaymentRequestDto paymentRequestDtoQuantityIsBiggest;
    AzericardResponseDto azericardResponseDtoFail;


    @BeforeEach
    void setUp() {

        paymentRequestDto = MockData.paymentRequestDto();
        paymentHistoryEntity = MockData.paymentHistoryEntity();
        azericardResponseDto = MockData.azericardResponseDto();
        paymentRequestDtoQuantityIsBiggest =
                MockData.paymentReqDtoQuantityIsBiggest();
        azericardResponseDtoFail = MockData.azericardResponseDtoFail();

//        paymentHistoryEntityQuantityIsBiggest =
//                MockData.paymentHistoryEntityQuantityIsBiggest();


    }

    @BeforeEach
    void setUpSecurityContext() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void whenPaymentIsSuccess() {
        // when
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto))
                .thenReturn(paymentHistoryEntity);

        when(productFeignClient.getCountsProducts(paymentRequestDto.productId()))
                .thenReturn("5");
        when(azericardService.payAzericard(paymentRequestDto))
                .thenReturn(azericardResponseDto);
        when(authentication.getName()).thenReturn("name");
        when(authentication.getCredentials()).thenReturn("password");

        //then
        paymentService.payProduct(paymentRequestDto);

        //verify
        verify(paymentHistoryMapper).mapDtoToEntity(paymentRequestDto);
        Assertions.assertEquals(paymentRequestDto.price(), BigDecimal.valueOf(paymentHistoryEntity.getPrice()));
        verify(paymentHistoryRepository, times(3)).save(paymentHistoryEntity);
        verify(productFeignClient).getCountsProducts(paymentRequestDto.productId());
        verify(azericardService).payAzericard(paymentRequestDto);
        verify(kafkaTemplate).send(anyString(), any());
        verify(productFeignClient).decreaseCount(any());
    }


    @Test
    void whenReqQuantityBigThanEntity() {
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto))
                .thenReturn(paymentHistoryEntity);

        when(productFeignClient.getCountsProducts(paymentRequestDto.productId()))
                .thenReturn("1");

        assertThrows(RuntimeException.class,
                () -> paymentService.payProduct(paymentRequestDto));

        verify(paymentHistoryMapper).mapDtoToEntity(paymentRequestDto);
        verify(paymentHistoryRepository, times(1)).save(paymentHistoryEntity);
        verify(productFeignClient).getCountsProducts(paymentRequestDto.productId());


    }

    @Test
    void whenAzericardPaymentIsFall() {
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto))
                .thenReturn(paymentHistoryEntity);

        when(productFeignClient.getCountsProducts(paymentRequestDto.productId()))
                .thenReturn("5");

        when(azericardService.payAzericard(paymentRequestDto))
                .thenReturn(azericardResponseDtoFail);

        assertThrows(RuntimeException.class,
                () -> paymentService.payProduct(paymentRequestDto));

        verify(paymentHistoryMapper).mapDtoToEntity(paymentRequestDto);
        verify(paymentHistoryRepository, times(3)).save(paymentHistoryEntity);
        verify(productFeignClient).getCountsProducts(paymentRequestDto.productId());
        verify(azericardService).payAzericard(paymentRequestDto);


    }
}