package com.rainiq.notificationservice.config;

import com.rainiq.notificationservice.topics.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic aiCompletedTopic()
    {
        return TopicBuilder.name(KafkaTopics.AI_COMPLETED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic complianceFailedTopic()
    {
        return TopicBuilder.name(KafkaTopics.COMPLIANCE_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
