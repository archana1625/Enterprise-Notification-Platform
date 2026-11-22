package com.enterprise.notification.delivery.config;

import com.enterprise.notification.event.NotificationEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    ConsumerFactory<String, NotificationEvent> notificationConsumerFactory(KafkaProperties properties) {
        return new DefaultKafkaConsumerFactory<>(
                properties.buildConsumerProperties(),
                new StringDeserializer(),
                new JsonDeserializer<>(NotificationEvent.class, false));
    }

    @Bean
    ProducerFactory<String, NotificationEvent> deadLetterProducerFactory(KafkaProperties properties) {
        return new DefaultKafkaProducerFactory<>(
                properties.buildProducerProperties(),
                new StringSerializer(),
                new JsonSerializer<>());
    }

    @Bean
    KafkaTemplate<String, NotificationEvent> deadLetterKafkaTemplate(
            ProducerFactory<String, NotificationEvent> deadLetterProducerFactory) {
        return new KafkaTemplate<>(deadLetterProducerFactory);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, NotificationEvent> consumerFactory,
            KafkaTemplate<String, NotificationEvent> deadLetterKafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(kafkaErrorHandler(deadLetterKafkaTemplate));
        return factory;
    }

    @Bean
    CommonErrorHandler kafkaErrorHandler(KafkaTemplate<String, NotificationEvent> deadLetterKafkaTemplate) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(deadLetterKafkaTemplate),
                new FixedBackOff(1_000L, 3L));
    }

    @Bean
    NewTopic notificationCreatedDeadLetterTopic(
            @Value("${app.kafka.topic.notification-created:notification-created}") String topic) {
        return TopicBuilder.name(topic + ".DLT").partitions(1).replicas(1).build();
    }
}
