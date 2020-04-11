package pl.diploma.thesis.agents.project.agent.provisioning.mysql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.diploma.thesis.agents.project.agent.events.EventPublisher;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;
import pl.diploma.thesis.agents.project.mysql.MySqlService;
import pl.diploma.thesis.agents.project.utils.ExceptionFormatter;

@Configuration
@EnableAsync
class ProvisioningAgentConfiguration {

    @Value("${database.provisioning.seconds.timeout}")
    private int provisioningSecondsTimeout;

    @Bean
    ProvisioningAgentService provisioningAgentService(DockerContainerInstanceService dockerContainerInstanceService,
                                                      ExceptionFormatter exceptionFormatter,
                                                      MySqlService mySqlService, EventPublisher eventPublisher) {
        return new ProvisioningAgentServiceImpl(mySqlService, dockerContainerInstanceService, exceptionFormatter,
                provisioningSecondsTimeout, eventPublisher);
    }

}
