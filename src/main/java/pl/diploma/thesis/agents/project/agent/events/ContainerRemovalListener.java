package pl.diploma.thesis.agents.project.agent.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.diploma.thesis.agents.project.agent.supervision.ContainerMaintenance;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

@Slf4j
@Component
@RequiredArgsConstructor
class ContainerRemovalListener implements ApplicationListener<ContainerRemovalEvent> {

    private final ContainerMaintenance containerMaintenance;

    @Override
    public void onApplicationEvent(ContainerRemovalEvent containerRemovalEvent) {
        DockerContainerInstanceDto message = containerRemovalEvent.getMessage();
        log.debug("Received containerRemovalEvent for containerId: " + message);
        containerMaintenance.waitForStopAndRemoveContainer(message);
    }
}
