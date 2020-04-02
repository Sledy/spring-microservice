package pl.diploma.thesis.agents.project.agent.events;


import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

public interface EventPublisher {

    void publishContainerRemovalEvent(DockerContainerInstanceDto dockerContainerInstanceDto);

}
