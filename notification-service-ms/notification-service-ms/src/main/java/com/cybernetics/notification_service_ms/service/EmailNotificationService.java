package com.cybernetics.notification_service_ms.service;

import com.cybernetics.notification_service_ms.dto.KafkaReqDto;

public interface EmailNotificationService {

    void sendEmail(KafkaReqDto mailSendReqDto);
}
