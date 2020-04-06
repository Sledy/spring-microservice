package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;

import java.util.List;

public interface DockerApi {

    List<Container> listContainers();

    void stopContainer(String containerId, int secondTimeout);

    boolean isContainerRunning(String containerId);

    void startContainer(String containerId);

    void removeContainer(String containerId);

    ContainerInfo inspectContainer(String containerId);

    ContainerConfig buildContainerConfig(DockerContainerConfigDto dockerContainerConfigDto);

    ContainerCreation createContainer(ContainerConfig containerConfig, String containerName);

}
