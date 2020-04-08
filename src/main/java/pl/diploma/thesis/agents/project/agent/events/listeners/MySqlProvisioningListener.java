package pl.diploma.thesis.agents.project.agent.events.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.diploma.thesis.agents.project.agent.events.Event;
import pl.diploma.thesis.agents.project.agent.provisioning.mysql.ProvisioningAgentService;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;

@Slf4j
@Component
@RequiredArgsConstructor
class MySqlProvisioningListener implements ApplicationListener<Event.MySqlProvisioning> {

    private final ProvisioningAgentService provisioningAgentService;

    @Override
    public void onApplicationEvent(Event.MySqlProvisioning event) {
        DockerContainerConfigDto message = event.getMessage();
        log.debug("Received MySqlProvisioning event for container: {}", message.getContainerName());
        provisioningAgentService.provisionMySqlDatabase(message);
    }
}
