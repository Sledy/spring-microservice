package pl.diploma.thesis.agents.project.agent.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

@RequiredArgsConstructor
class EventPublisherImpl implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishContainerRemovalEvent(DockerContainerInstanceDto dockerContainerInstanceDto) {
        Event.ContainerRemoval event = Event.getContainerRemovalEvent(this, dockerContainerInstanceDto);
        publisher.publishEvent(event);
    }

    @Override
    public void publishMySqlProvisioningEvent(DockerContainerConfigDto dockerContainerConfigDto) {
        Event.MySqlProvisioning event = Event.getMySqlProvisioningEvent(this, dockerContainerConfigDto);
        publisher.publishEvent(event);
    }

    @Override
    public void publishFailoverEvent(DockerContainerInstanceDto dockerContainerInstanceDto,
                                     DockerContainerConfigDto dockerContainerConfigDto) {
        Event.DatabaseFailoverEvent event = Event
                .getDatabseFailoverEvent(this, dockerContainerConfigDto, dockerContainerInstanceDto);
        publisher.publishEvent(event);
    }

}
