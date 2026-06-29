package com.rainiq.complianceservice.config;

import com.rainiq.complianceservice.topics.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic designSubmittedTopic()
    {
        return TopicBuilder.name(KafkaTopics.DESIGN_SUBMITTED)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic complianceCompletedTopic()
    {
        return TopicBuilder.name(KafkaTopics.COMPLIANCE_COMPLETED)
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
