package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.ContainerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.diploma.thesis.agents.project.utils.JsonConverter;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
class DockerContainerInstanceServiceImpl implements DockerContainerInstanceService {

    private final DockerContainerInstanceRepository repository;
    private final JsonConverter<DockerContainerInstance> jsonConverter;
    private final DockerApi dockerApi;
    private final DockerContainerInstanceMapper dockerContainerInstanceMapper;

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

    @Override
    public boolean updateAllDockerInstancesInfo() {
        dockerApi.listContainers()
                .stream()
                .map(container -> dockerApi.inspectContainer(container.id()))
                .map(this::mapContainerInfoToDockerInstanceAndSetIdAndTime)
                .forEach(repository::save);
        return true;
    }


    private DockerContainerInstance mapContainerInfoToDockerInstanceAndSetIdAndTime(ContainerInfo containerInfo) {
        DockerContainerInstanceDto dto = dockerContainerInstanceMapper
                .mapContainerInfoToDockerInstanceDto(containerInfo);
        Long id = repository.findDockerContainerInstanceByContainerId(containerInfo.id()).getId();
        dto.setId(id);
        dto.setLastStatusUpdate(LocalDateTime.now());
        return dockerContainerInstanceMapper.mapToDockerContainerInstance(dto);
    }
}
