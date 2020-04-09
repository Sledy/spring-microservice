package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public void updateAllDockerInstancesInfo() {
        dockerApi.listContainers()
                 .stream()
                 .map(container -> dockerApi.inspectContainer(container.id()))
                 .map(this::createOrGetContainer)
                 .forEach(repository::save);
        log.debug("Containers status updated");
    }

    @Override
    public void removeContainer(DockerContainerInstanceDto dockerContainerInstanceDto) {
        dockerApi.removeContainer(dockerContainerInstanceDto.getContainerId());
        log.debug("Removing container with id: " + dockerContainerInstanceDto.getContainerId());
    }

    @Override
    public void purgeRemovedContainerFromDB(DockerContainerInstanceDto dockerContainerInstanceDto) {
        DockerContainerInstance container =
                dockerContainerInstanceMapper.mapToDockerContainerInstance(dockerContainerInstanceDto);
        repository.delete(container);
        log.debug("DB record deleted for container id: " + dockerContainerInstanceDto.getContainerId());
    }

    @Override
    public void deleteContainer(String containerId) {
        Optional<DockerContainerInstance> instance = repository.findDockerContainerInstanceByContainerId(containerId);
        if (instance.isEmpty()) {
            throw new ContainerNotExistsException(String.format("Container does not exists, id: %s", containerId));
        }
        DockerContainerInstanceDto dto = dockerContainerInstanceMapper.mapToDockerContainerInstanceDto(instance.get());
        stopContainer(dto);
        eventPublisher.publishContainerRemovalEvent(dto);
    }

    @Override
    public void stopContainer(DockerContainerInstanceDto dockerContainerInstanceDto) {
        dockerApi.stopContainer(dockerContainerInstanceDto.getContainerId(), 10);
        log.debug("Stopping container with id: " + dockerContainerInstanceDto.getContainerId());
    }

    @Override
    public List<DockerContainerInstanceDto> listAllContainers() {
        return StreamSupport.stream(repository.findAll()
                                              .spliterator(), false)
                            .map(dockerContainerInstanceMapper::mapToDockerContainerInstanceDto)
                            .collect(Collectors.toList());
    }

    @Override
    public String createDockerContainer(DockerContainerConfigDto dockerContainerConfigDto) {
        ContainerConfig config = dockerApi.buildContainerConfig(dockerContainerConfigDto);
        ContainerCreation container = dockerApi.createContainer(config);
        log.info("Container with {} created", container.id());
        return container.id();
    }

    @Override
    public DockerContainerInstanceDto persistContainerDetailsInDb(String containerId) {
        Optional<DockerContainerInstance> instanceOptional =
                repository.findDockerContainerInstanceByContainerId(containerId);

        DockerContainerInstance entity = instanceOptional.orElseGet(() -> dockerContainerInstanceMapper
                .mapContainerInfoToDockerContainerInstance(dockerApi.inspectContainer(containerId)));

        entity.setLastStatusUpdate(LocalDateTime.now());
        return dockerContainerInstanceMapper.mapToDockerContainerInstanceDto(repository.save(entity));
    }

    @Override
    public void startContainer(DockerContainerInstanceDto dockerContainerInstanceDto) {
        dockerApi.startContainer(dockerContainerInstanceDto.getContainerId());
    }

    @Override
    public ContainerStateEnum getContainerState(DockerContainerInstanceDto dockerContainerInstanceDto) {
        String status = dockerApi.inspectContainer(dockerContainerInstanceDto.getContainerId())
                                 .state()
                                 .status();
        return ContainerStateEnum.fromValue(status);
    }

    @Override
    public List<String> getContainerProcessesList(DockerContainerInstanceDto dockerContainerInstanceDto) {
        return dockerApi.listContainerProcesses(dockerContainerInstanceDto)
                        .processes()
                        .stream()
                        .map(process -> process.get(process.size() - 1))
                        .collect(Collectors.toList());
    }

    @Override
    public Long registerContainerInDb(DockerContainerConfigDto dockerContainerConfigDto) {
        DockerContainerInstance dockerContainerInstance = new DockerContainerInstance();
        dockerContainerInstance.setImageName(dockerContainerConfigDto.getImage());
        dockerContainerInstance.setContainerName(dockerContainerConfigDto.getContainerName());
        dockerContainerInstance.setLastStatusUpdate(LocalDateTime.now());
        return repository.save(dockerContainerInstance)
                         .getId();
    }


    private DockerContainerInstance createOrGetContainer(ContainerInfo containerInfo) {
        Optional<DockerContainerInstance> optional =
                repository.findDockerContainerInstanceByContainerId(containerInfo.id());
        DockerContainerInstance entity = optional
                .orElseGet(() -> dockerContainerInstanceMapper
                        .mapContainerInfoToDockerContainerInstance(containerInfo));
        entity.setLastStatusUpdate(LocalDateTime.now());
        return entity;
    }
}
