package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.req.DecreaseCountReqDto;
import com.cybernetics.payment_ms.dto.req.KafkaReqDto;
import com.cybernetics.payment_ms.dto.req.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.res.AzericardResponseDto;
import com.cybernetics.payment_ms.dto.res.PaymentResponseDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import com.cybernetics.payment_ms.exception.EnoughProductException;
import com.cybernetics.payment_ms.exception.PaymentFailedException;
import com.cybernetics.payment_ms.feign.ProductFeignClient;
import com.cybernetics.payment_ms.mapper.PaymentHistoryMapper;
import com.cybernetics.payment_ms.repository.PaymentHistoryRepository;
import com.cybernetics.payment_ms.service.PaymentService;
import com.cybernetics.payment_ms.utils.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.cybernetics.payment_ms.utils.PaymentStatus.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final String TOPIC = "kafka-payment-topic";

    private final AzericardServiceImpl azericardService;
    private final ProductFeignClient productFeignClient;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final KafkaTemplate<String, KafkaReqDto> kafkaTemplate;

    @Override
    @Transactional
    public PaymentResponseDto payProduct(PaymentRequestDto paymentRequestDto) {
        PaymentHistoryEntity paymentHistoryEntity =
                paymentHistoryMapper.mapDtoToEntity(paymentRequestDto);

        paymentHistoryEntity.setStatus(NEW.name());

        paymentHistoryRepository.save(paymentHistoryEntity);

        String countsProducts =
                productFeignClient.getCountsProducts(paymentRequestDto.productId());

        if (paymentRequestDto.quantity() > Integer.valueOf(countsProducts)) {
            throw new EnoughProductException("Not enough products");
        }

        paymentHistoryEntity.setStatus(PENDING.name());
        paymentHistoryRepository.save(paymentHistoryEntity);

        AzericardResponseDto azericardResponseDto =
                azericardService.payAzericard(paymentRequestDto);

        if (!"1".equals(azericardResponseDto.status())) {

            paymentHistoryEntity.setAzericardStatus(azericardResponseDto.status());
            paymentHistoryEntity.setStatus(PAYMENT_FAILED.name());
            paymentHistoryRepository.save(paymentHistoryEntity);

            throw new PaymentFailedException("Payment failed");
        }

        paymentHistoryEntity.setOrderId(azericardResponseDto.orderId());
        paymentHistoryEntity.setAzericardStatus(azericardResponseDto.status());
        paymentHistoryEntity.setStatus(PaymentStatus.SUCCESS.name());

        paymentHistoryRepository.save(paymentHistoryEntity);


        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String userName = auth.getCredentials().toString();

        kafkaTemplate.send(TOPIC, KafkaReqDto.builder()
                .email(email)
                .orderId(azericardResponseDto.orderId())
                .userName(userName)
                .build());

        productFeignClient.decreaseCount(DecreaseCountReqDto.builder()
                .count(paymentHistoryEntity.getQuantity())
                .productId(paymentHistoryEntity.getProductId())
                .build());

        return PaymentResponseDto.builder()
                .orderId(paymentHistoryEntity.getOrderId())
                .status(paymentHistoryEntity.getStatus())
                .build();
    }
}
