package com.cybernetics.payment_ms.service;

import com.cybernetics.payment_ms.dto.req.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.res.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto payProduct(PaymentRequestDto paymentRequestDto);
}
