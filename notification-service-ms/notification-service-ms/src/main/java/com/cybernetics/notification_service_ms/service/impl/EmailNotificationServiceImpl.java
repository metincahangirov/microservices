package com.cybernetics.notification_service_ms.service.impl;

import com.cybernetics.notification_service_ms.dto.KafkaReqDto;
import com.cybernetics.notification_service_ms.service.EmailNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailNotificationServiceImpl implements EmailNotificationService {

    JavaMailSender javaMailSender;

    @Override
    public void sendEmail(KafkaReqDto mailSendReqDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailSendReqDto.email());
        message.setSubject("Payment uğurla tamamlandı");
        message.setText(
                "Salam " + mailSendReqDto.userName() + ",\n\n" +
                        "Sizin sifarişiniz qəbul edildi.\n" +
                        "Order ID: " + mailSendReqDto.orderId()
        );
        javaMailSender.send(message);

    }
}
