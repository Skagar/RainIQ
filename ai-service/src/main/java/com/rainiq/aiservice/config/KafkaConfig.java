package com.rainiq.aiservice.config;

import com.rainiq.aiservice.topics.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic complianceCompletedTopic()
    {
        return TopicBuilder.name(KafkaTopics.COMPLIANCE_COMPLETED)
                .replicas(1)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic aiCompletedTopic()
    {
        return TopicBuilder.name(KafkaTopics.AI_COMPLETED)
                .replicas(1)
                .partitions(1)
                .build();
    }
}
