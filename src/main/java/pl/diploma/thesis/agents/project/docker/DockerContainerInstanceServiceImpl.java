package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.ContainerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.diploma.thesis.agents.project.utils.JsonConverter;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DockerContainerInstanceServiceImpl implements DockerContainerInstanceService {

    private final DockerContainerInstanceRepository repository;
    private final JsonConverter<DockerContainerInstance> jsonConverter;

    @Override
    public void saveDockerInstanceInfo(ContainerInfo containerInfo) {
        DockerContainerInstance dockerContainerInstance = new DockerContainerInstance();
        dockerContainerInstance.setContainerId(containerInfo.id());
        dockerContainerInstance.setImageName(containerInfo.image());
        dockerContainerInstance.setStatus(containerInfo.state().status());
        dockerContainerInstance.setLastStatusUpdate(LocalDateTime.now());
        DockerContainerInstance record = repository.save(dockerContainerInstance);
        log.info(jsonConverter.convertToJsonString(record));
    }

    @Override
    public void saveAllDockerInstancesInfo(List<ContainerInfo> containerInfoList) {
        containerInfoList.forEach(this::saveDockerInstanceInfo);
    }
}
