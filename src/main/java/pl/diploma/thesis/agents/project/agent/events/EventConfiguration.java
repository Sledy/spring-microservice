package pl.diploma.thesis.agents.project.agent.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EventConfiguration {

    @Bean
    EventPublisher eventPublisher(ApplicationEventPublisher applicationEventPublisher){
        return new EventPublisherImpl(applicationEventPublisher);
    }

}
