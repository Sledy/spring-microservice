package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.ContainerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.diploma.thesis.agents.project.agent.events.EventPublisher;
import pl.diploma.thesis.agents.project.docker.exceptions.ContainerNotExistsException;
import pl.diploma.thesis.agents.project.utils.JsonConverter;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
class DockerContainerInstanceServiceImpl implements DockerContainerInstanceService {

    private final DockerContainerInstanceRepository repository;
    private final JsonConverter<DockerContainerInstance> jsonConverter;
    private final DockerApi dockerApi;
    private final DockerContainerInstanceMapper dockerContainerInstanceMapper;
    private final EventPublisher eventPublisher;

    @Override
    public void saveDockerInstanceInfo(DockerContainerInstanceDto containerInstanceDto) {
        DockerContainerInstance dockerContainerInstance = new DockerContainerInstance();
        dockerContainerInstance.setContainerId(containerInstanceDto.getContainerId());
        dockerContainerInstance.setImageName(containerInstanceDto.getImageName());
        dockerContainerInstance.setStatus(containerInstanceDto.getStatus());
        dockerContainerInstance.setLastStatusUpdate(LocalDateTime.now());
        DockerContainerInstance record = repository.save(dockerContainerInstance);
        log.trace(jsonConverter.convertToJsonString(record));
    }

    @Override
    public void saveAllDockerInstancesInfo(List<DockerContainerInstanceDto> containerInstanceDtoListList) {
        containerInstanceDtoListList.forEach(this::saveDockerInstanceInfo);
    }

    @Override
    public boolean updateAllDockerInstancesInfo() {
        dockerApi.listContainers()
                .stream()
                .map(container -> dockerApi.inspectContainer(container.id()))
                .map(this::mapContainerInfoToDockerInstanceAndSetIdAndTime)
                .forEach(repository::save);
        log.debug("Containers status updated");
        return true;
    }

    @Override
    public boolean removeContainer(DockerContainerInstanceDto dockerContainerInstanceDto) {
        dockerApi.removeContainer(dockerContainerInstanceDto.getContainerId());
        log.debug("Removing container with id: " + dockerContainerInstanceDto.getContainerId());
        return true;
    }

    @Override
    public boolean purgeRemovedContainerFromDB(DockerContainerInstanceDto dockerContainerInstanceDto) {
        repository.delete(dockerContainerInstanceMapper.mapToDockerContainerInstance(dockerContainerInstanceDto));
        log.debug("DB record deleted for container id: " + dockerContainerInstanceDto.getContainerId());
        return true;
    }

    @Override
    public boolean deleteContainer(String containerId) {
        Optional<DockerContainerInstance> instance = repository.findDockerContainerInstanceByContainerId(containerId);
        if(instance.isEmpty()){
            throw new ContainerNotExistsException();
        }
        DockerContainerInstanceDto dto = dockerContainerInstanceMapper.mapToDockerContainerInstanceDto(instance.get());
        stopContainer(dto);
        eventPublisher.publishContainerRemovalEvent(dto);
        return false;
    }

    @Override
    public boolean stopContainer(DockerContainerInstanceDto dockerContainerInstanceDto) {
        dockerApi.stopContainer(dockerContainerInstanceDto.getContainerId(), 10);
        log.debug("Stopping container with id: " + dockerContainerInstanceDto.getContainerId());
        return true;
    }


    private DockerContainerInstance mapContainerInfoToDockerInstanceAndSetIdAndTime(ContainerInfo containerInfo) {
        Optional<DockerContainerInstance> optional = repository.findDockerContainerInstanceByContainerId(containerInfo.id());
        if(optional.isEmpty()){
            throw new ContainerNotExistsException();
        }
        DockerContainerInstance entity = optional.get();
        entity.setLastStatusUpdate(LocalDateTime.now());
        return entity;
    }
}
