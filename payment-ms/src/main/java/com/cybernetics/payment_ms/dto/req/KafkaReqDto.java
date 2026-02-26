package com.cybernetics.payment_ms.dto.req;

import lombok.Builder;

@Builder
public record KafkaReqDto(String email,
                          String orderId,
                          String userName) {
}
