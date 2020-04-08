package pl.diploma.thesis.agents.project.agent.events;

import org.springframework.context.ApplicationEvent;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

public class Event {

    private Event() {
    }

    static ContainerRemoval getContainerRemovalEvent(Object source, DockerContainerInstanceDto containerInstanceDto) {
        return new ContainerRemoval(source, containerInstanceDto);
    }

    static MySqlProvisioning getMySqlProvisioningEvent(Object source, DockerContainerConfigDto dockerContainerConfigDto){
        return new MySqlProvisioning(source, dockerContainerConfigDto);
    }

    public static class ContainerRemoval extends ApplicationEvent {
        private transient DockerContainerInstanceDto containerInstanceDto;

        private ContainerRemoval(Object source, DockerContainerInstanceDto containerInstanceDto) {
            super(source);
            this.containerInstanceDto = containerInstanceDto;
        }

        public DockerContainerInstanceDto getMessage() {
            return containerInstanceDto;
        }
    }

    public static class MySqlProvisioning extends ApplicationEvent{

        private transient DockerContainerConfigDto dockerContainerConfigDto;

        private MySqlProvisioning(Object source, DockerContainerConfigDto dockerContainerConfigDto) {
            super(source);
            this.dockerContainerConfigDto = dockerContainerConfigDto;
        }

        public DockerContainerConfigDto getMessage(){
            return dockerContainerConfigDto;
        }
    }

}
