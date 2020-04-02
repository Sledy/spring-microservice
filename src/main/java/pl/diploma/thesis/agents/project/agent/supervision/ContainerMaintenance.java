package pl.diploma.thesis.agents.project.agent.supervision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.diploma.thesis.agents.project.docker.ContainerStateEnum;
import pl.diploma.thesis.agents.project.docker.DockerApi;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;
import pl.diploma.thesis.agents.project.utils.ExceptionFormatter;

@Slf4j
@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class ContainerMaintenance {

    private final DockerContainerInstanceService dockerContainerInstanceService;
    private final DockerApi dockerApi;
    private final ExceptionFormatter exceptionFormatter;

    @Async
    @Scheduled(fixedRate = 10000)
    void updateContainersStatus() {
        dockerContainerInstanceService.updateAllDockerInstancesInfo();
    }

    @Async
    public void waitForStopAndRemoveContainer(DockerContainerInstanceDto dockerContainerInstanceDto) {
        while (true) {
            ContainerStateEnum stateEnum = ContainerStateEnum.fromValue(dockerApi
                    .inspectContainer(dockerContainerInstanceDto.getContainerId())
                    .state()
                    .status());
            if (stateEnum == ContainerStateEnum.EXITED) {
                dockerApi.removeContainer(dockerContainerInstanceDto.getContainerId());
                dockerContainerInstanceService.purgeRemovedContainerFromDB(dockerContainerInstanceDto);
                return;
            }
            try {
                log.debug("Waiting for container to stop. ContainerId: " + dockerContainerInstanceDto.getContainerId());
                Thread.sleep(10000);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
                log.error("Thread interrupted during waiting for container state change");
                log.error(exceptionFormatter.formatStackTrace(exc));
            }
        }
    }

}
