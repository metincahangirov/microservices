package com.cybernetics.payment_ms.dto.res;

import lombok.Builder;

@Builder
public record AzericardResponseDto(
        String orderId,
        String status
) {
}
