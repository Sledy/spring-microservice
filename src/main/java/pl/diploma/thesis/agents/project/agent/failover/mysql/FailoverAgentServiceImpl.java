package pl.diploma.thesis.agents.project.agent.failover.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import pl.diploma.thesis.agents.project.docker.ContainerStateEnum;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
class FailoverAgentServiceImpl implements FailoverAgentService {

    private final DockerContainerInstanceService dockerContainerInstanceService;
    private static final List<ContainerStateEnum> FAILOVER_TRIGGERING_STATUSES;

    static {
        FAILOVER_TRIGGERING_STATUSES = List.of(ContainerStateEnum.EXITED, ContainerStateEnum.DEAD,
                ContainerStateEnum.UNKNOWN);
    }

    @Async
    @Override
    public void executeDatabaseInstanceFailover(DockerContainerInstanceDto dockerContainerInstanceDto,
                                                DockerContainerConfigDto dockerContainerConfigDto) {
        ContainerStateEnum containerState =
                dockerContainerInstanceService.getContainerState(dockerContainerInstanceDto);

        if (instanceHealthy(containerState)) {
            log.info("Failover aborted for container: {}", dockerContainerInstanceDto.getContainerId());
            return;
        }

        if (instanceStopped(containerState)) {
            log.info("Container is stopped. Try to restart container: {}", dockerContainerInstanceDto.getContainerId());
            if (tryToRestartContainer(dockerContainerInstanceDto, 10)) {
                log.info("Container restarted");
                return;
            }
            log.info("Timeout when starting container");
        }

        log.warn("Failover failed. Container lost");
    }


    private boolean tryToRestartContainer(DockerContainerInstanceDto dockerContainerInstanceDto, int secondTimeout) {
        dockerContainerInstanceService.startContainer(dockerContainerInstanceDto);
        for (int i = 0; i < secondTimeout; i++) {
            try {
                if (dockerContainerInstanceService
                        .getContainerState(dockerContainerInstanceDto) == ContainerStateEnum.RUNNING) {
                    return true;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Thread interrupted");
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }


    private boolean instanceHealthy(ContainerStateEnum instanceState) {
        return !FAILOVER_TRIGGERING_STATUSES.contains(instanceState);
    }

    private boolean instanceStopped(ContainerStateEnum instanceState) {
        return instanceState == ContainerStateEnum.PAUSED || instanceState == ContainerStateEnum.EXITED;
    }


}
