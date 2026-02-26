package com.cybernetics.notification_service_ms.service.impl;


import com.cybernetics.notification_service_ms.dto.KafkaReqDto;
import com.cybernetics.notification_service_ms.service.EmailNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaConsumer {

    EmailNotificationService emailNotificationService;
    KafkaTemplate<String, KafkaReqDto> kafkaTemplate;


    @KafkaListener(topics = "kafka-payment-topic",
            groupId = "notification-group")
    public void consume(KafkaReqDto dto,
                        Acknowledgment ack) {
        log.info("Received message: {}", dto);

        emailNotificationService.sendEmail(dto);
        log.info("Message sent successfully");
        ack.acknowledge();


    }
}
