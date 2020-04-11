package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.*;

import java.util.List;
import java.util.Optional;

public interface DockerApi {

    List<Container> listContainers();

    void stopContainer(String containerId, int secondTimeout);

    boolean isContainerRunning(String containerId);

    void startContainer(String containerId);

    void removeContainer(String containerId);

    ContainerInfo inspectContainer(String containerId);

    ContainerConfig buildContainerConfig(DockerContainerConfigDto dockerContainerConfigDto);

    ContainerCreation createContainer(ContainerConfig containerConfig);

    Optional<TopResults> listContainerProcesses(DockerContainerInstanceDto dockerContainerInstanceDto);

}
