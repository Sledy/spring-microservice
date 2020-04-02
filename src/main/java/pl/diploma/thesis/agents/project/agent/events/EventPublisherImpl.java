package pl.diploma.thesis.agents.project.agent.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

@RequiredArgsConstructor
class EventPublisherImpl implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishContainerRemovalEvent(DockerContainerInstanceDto dockerContainerInstanceDto) {
        ContainerRemovalEvent event = new ContainerRemovalEvent(this, dockerContainerInstanceDto);
        publisher.publishEvent(event);
    }

}
