package com.cybernetics.notification_service_ms.dto;

public record MailSendReqDto(String email,
                             String subject,
                             String body) {
}
