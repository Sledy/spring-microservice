package pl.diploma.thesis.agents.project.agent.events;


import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

public interface EventPublisher {

    void publishContainerRemovalEvent(DockerContainerInstanceDto dockerContainerInstanceDto);

    void publishMySqlProvisioningEvent(DockerContainerConfigDto dockerContainerConfigDto);

}
