package pl.diploma.thesis.agents.project.agent.failover.mysql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

@Configuration
@EnableAsync
class FailoverAgentConfiguration {

    @Bean
    FailoverAgentService failoverAgentService(DockerContainerInstanceService dockerContainerInstanceService){
        return new FailoverAgentServiceImpl(dockerContainerInstanceService);
    }

}
