package com.rainiq.monitoringservice.config;

import com.rainiq.monitoringservice.topic.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic monitoringAlertTopic()
    {
        return TopicBuilder.name(KafkaTopics.MONITORING_ALERT)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
