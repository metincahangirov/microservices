package com.cybernetics.notification_service_ms.config;//package com.cybernetics.notification_service_ms.config;
//
//import com.cybernetics.notification_service_ms.dto.KafkaReqDto;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//
//        Map<String, Object> props = new HashMap<>();
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//
//        return props;
//    }
//
//    @Bean
//    public ConsumerFactory<String, KafkaReqDto> consumerFactory() {
//
//        JsonDeserializer<KafkaReqDto> deserializer =
//                new JsonDeserializer<>(KafkaReqDto.class);
//
//        deserializer.addTrustedPackages("*");
//
//        return new DefaultKafkaConsumerFactory<>(
//                consumerConfigs(),
//                new StringDeserializer(),
//                deserializer
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaReqDto>
//    kafkaListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, KafkaReqDto> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setConsumerFactory(consumerFactory());
//
//        factory.getContainerProperties()
//                .setAckMode(ContainerProperties.AckMode.MANUAL);
//
//        return factory;
//    }
//}
