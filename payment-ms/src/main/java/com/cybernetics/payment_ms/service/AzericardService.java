package com.cybernetics.payment_ms.service;

import com.cybernetics.payment_ms.dto.req.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.res.AzericardResponseDto;

public interface AzericardService {

    AzericardResponseDto payAzericard(PaymentRequestDto paymentRequestDto);
}
