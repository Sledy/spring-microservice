package pl.diploma.thesis.agents.project.agent.events

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

@TestConfiguration
class EventTestConfiguration {

    private DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    EventPublisher testEventPublisher(){
        return detachedMockFactory.Mock(EventPublisher)
    }

}
