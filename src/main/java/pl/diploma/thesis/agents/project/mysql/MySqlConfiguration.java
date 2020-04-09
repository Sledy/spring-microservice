package pl.diploma.thesis.agents.project.mysql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

@Configuration
class MySqlConfiguration {

    @Bean
    MySqlService mySqlService(DockerContainerInstanceService dockerContainerInstanceService){
        return new MySqlServiceImpl(dockerContainerInstanceService);
    }

}
