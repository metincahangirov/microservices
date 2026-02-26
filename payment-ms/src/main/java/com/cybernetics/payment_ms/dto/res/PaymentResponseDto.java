package com.cybernetics.payment_ms.dto.res;

import lombok.Builder;

@Builder
public record PaymentResponseDto(String orderId,
                                 String status) {
}
