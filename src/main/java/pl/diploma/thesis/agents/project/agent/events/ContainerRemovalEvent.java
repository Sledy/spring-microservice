package pl.diploma.thesis.agents.project.agent.events;

import org.springframework.context.ApplicationEvent;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

class ContainerRemovalEvent extends ApplicationEvent {

    private transient DockerContainerInstanceDto containerInstanceDto;

    public ContainerRemovalEvent(Object source, DockerContainerInstanceDto containerInstanceDto) {
        super(source);
        this.containerInstanceDto = containerInstanceDto;
    }

    public DockerContainerInstanceDto getMessage(){
        return containerInstanceDto;
    }
}
