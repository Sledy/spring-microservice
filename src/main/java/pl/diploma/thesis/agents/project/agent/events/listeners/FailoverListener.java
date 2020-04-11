package pl.diploma.thesis.agents.project.agent.events.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.diploma.thesis.agents.project.agent.events.Event;
import pl.diploma.thesis.agents.project.agent.failover.mysql.FailoverAgentService;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

@Slf4j
@Component
@RequiredArgsConstructor
class FailoverListener implements ApplicationListener<Event.DatabaseFailoverEvent> {

    private final FailoverAgentService failoverAgentService;

    @Override
    public void onApplicationEvent(Event.DatabaseFailoverEvent databaseFailoverEvent) {
        DockerContainerInstanceDto dockerContainerInstanceDto = databaseFailoverEvent.getDockerContainerInstanceDto();
        log.debug("Received failover event for container {}", dockerContainerInstanceDto.getContainerId());
        failoverAgentService.executeDatabaseInstanceFailover(dockerContainerInstanceDto,
                databaseFailoverEvent.getDockerContainerConfigDto());
    }
}
