package com.cybernetics.notification_service_ms.dto;

import lombok.Builder;

@Builder
public record KafkaReqDto(String email,
                          String orderId,
                          String userName) {
}
