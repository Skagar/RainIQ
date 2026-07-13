package com.rainiq.notificationservice.config;

import com.rainiq.notificationservice.event.AiCompletedEvent;
import com.rainiq.notificationservice.event.ComplianceFailedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Bean
    public ConsumerFactory<String, AiCompletedEvent> aiCompletedConsumerFactory()
    {
        Map<String,Object> props=new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,AiCompletedEvent.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS,false);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConsumerFactory<String, ComplianceFailedEvent> complianceFailedConsumerFactory()
    {
        Map<String,Object> props=new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,ComplianceFailedEvent.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS,false);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,AiCompletedEvent> aiCompletedEventConcurrentKafkaListenerContainerFactory(ConsumerFactory<String,AiCompletedEvent> aiCompletedConsumerFactory )
    {
        ConcurrentKafkaListenerContainerFactory<String,AiCompletedEvent> factory=
        new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(aiCompletedConsumerFactory );
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,ComplianceFailedEvent> complianceFailedEventConcurrentKafkaListenerContainerFactory(ConsumerFactory<String,ComplianceFailedEvent> complianceFailedConsumerFactory)
    {
        ConcurrentKafkaListenerContainerFactory<String,ComplianceFailedEvent> factory=
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(complianceFailedConsumerFactory);
        return factory;
    }

}
