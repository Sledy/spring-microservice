package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.messages.ContainerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.diploma.thesis.agents.project.agent.events.EventPublisher;
import pl.diploma.thesis.agents.project.utils.JsonConverter;

@Configuration
@RequiredArgsConstructor
class DockerConfiguration {

    private final DockerContainerInstanceRepository repository;
    private final JsonConverter<DockerContainerInstance> jsonConverter;

    @Bean
    DockerClient dockerClient() throws DockerCertificateException {
        return DefaultDockerClient.fromEnv().build();
    }

    @Bean
    DockerApi dockerApi(DockerClient dockerClient) {
        return new DockerApiImpl(dockerClient);
    }

    @Bean
    DockerContainerInstanceService dockerContainerInstanceService(DockerApi dockerApi,
                                                                  DockerContainerInstanceMapper dockerContainerInstanceMapper,
                                                                  EventPublisher eventPublisher) {
        return new DockerContainerInstanceServiceImpl(repository, jsonConverter, dockerApi,
                dockerContainerInstanceMapper, eventPublisher);
    }

    @Bean
    ContainerConfig mariaDbContainerConfig() {
        return ContainerConfig.builder().build();
    }

    @Bean
    DockerContainerInstanceMapper dockerContainerInstanceMapper() {
        return DockerContainerInstanceMapper.INSTANCE;
    }
}
