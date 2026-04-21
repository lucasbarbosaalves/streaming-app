package com.github.lucasbarbosaalves.catalog.infrastructure.config;

import com.github.lucasbarbosaalves.catalog.infrastructure.config.annotations.VideoCreatedQueue;
import com.github.lucasbarbosaalves.catalog.infrastructure.config.properties.amqp.QueueProperties;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.EventService;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    EventService videoCreatedEventService(@VideoCreatedQueue QueueProperties props, RabbitOperations ops) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
