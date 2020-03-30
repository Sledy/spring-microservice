package pl.diploma.thesis.agents.project.agent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.diploma.thesis.agents.project.docker.DockerApi;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

@Component
@Slf4j
@RequiredArgsConstructor
class InitContainerScan implements ApplicationListener<ContextRefreshedEvent> {

    private final DockerContainerInstanceService dockerContainerInstanceService;
    private final DockerApi dockerApi;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dockerApi.listContainers().stream()
                .map(container -> dockerApi.inspectContainer(container.id()))
                .forEach(dockerContainerInstanceService::saveDockerInstanceInfo);
    }
}
