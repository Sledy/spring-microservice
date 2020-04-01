package pl.diploma.thesis.agents.project.docker

import com.fasterxml.jackson.databind.ObjectMapper
import com.spotify.docker.client.DefaultDockerClient
import com.spotify.docker.client.DockerClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pl.diploma.thesis.agents.project.utils.ExceptionFormatter
import pl.diploma.thesis.agents.project.utils.JsonConverter
import spock.mock.DetachedMockFactory


@TestConfiguration
class DockerTestConfiguration {

    private DockerContainerInstanceMapper testDockerContainerInstanceMapper = DockerContainerInstanceMapper.INSTANCE
    private DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    DockerContainerInstanceService testDockerContainerInstanceService(DockerContainerInstanceRepository testDockerContainerRepository,
                                                                      JsonConverter<DockerContainerInstance> testJsonConverter,
                                                                      DockerApi testDockerApi) {
        return new DockerContainerInstanceServiceImpl(testDockerContainerRepository,
                testJsonConverter, testDockerApi, testDockerContainerInstanceMapper)
    }

    @Bean
    DockerApi testDockerApi(DockerClient testDockerClient) {
        return new DockerApiImpl(testDockerClient)
    }

    @Bean
    DockerClient testDockerClient() {
        return DefaultDockerClient.fromEnv().build()
    }

    @Bean
    JsonConverter<DockerContainerInstance> testJsonConverter() {
        return new JsonConverter<DockerContainerInstance>(new ObjectMapper(), new ExceptionFormatter())
    }

    @Bean
    DockerContainerInstanceRepository testDockerContainerRepository(){
        return detachedMockFactory.Mock(DockerContainerInstanceRepository)
    }

}
