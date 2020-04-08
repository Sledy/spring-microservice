package pl.diploma.thesis.agents.project.agent.provisioning.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import pl.diploma.thesis.agents.project.docker.ContainerStateEnum;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;
import pl.diploma.thesis.agents.project.utils.ExceptionFormatter;

@Slf4j
@RequiredArgsConstructor
class ProvisioningAgentServiceImpl implements ProvisioningAgentService {

    private final DockerContainerInstanceService dockerContainerInstanceService;
    private final ExceptionFormatter exceptionFormatter;
    private final int provisioningSecondsTimeout;

    @Async
    @Override
    public void provisionMySqlDatabase(DockerContainerConfigDto dockerContainerConfigDto) {
        log.debug("Creating container");
        String containerId = dockerContainerInstanceService.createDockerContainer(dockerContainerConfigDto);
        log.info("Container created with id {}", containerId);

        log.debug("Persisting container data in database");
        DockerContainerInstanceDto dockerContainerInstanceDto =
                dockerContainerInstanceService.persistContainerDetailsInDb(containerId);
        log.debug("Data persisted in database. Starting container");

        dockerContainerInstanceService.startContainer(dockerContainerInstanceDto);
        log.info("Container started. Waiting to be ready");
        waitForContainerToBeRunning(dockerContainerInstanceDto, provisioningSecondsTimeout);
        log.info("Container is running. Checking database health");


    }

    private void waitForContainerToBeRunning(DockerContainerInstanceDto dockerContainerInstanceDto,
                                             int provisioningSecondsTimeout) {
        int milliSecondsInterval = 1000;
        int maxRetries = provisioningSecondsTimeout / milliSecondsInterval;
        int retries = 0;

        while (!containerIsRunning(dockerContainerInstanceDto) && maxRetries > retries) {
            try {
                Thread.sleep(1000);
                retries++;
            } catch (InterruptedException e) {
                log.error("{} interrupted ", Thread.currentThread().getName());
                log.error(exceptionFormatter.formatStackTrace(e));
                Thread.currentThread().interrupt();
            }
        }
    }


    private boolean containerIsRunning(DockerContainerInstanceDto dto) {
        return dockerContainerInstanceService.getContainerState(dto) == ContainerStateEnum.RUNNING;
    }

}
