package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerInfo;

import java.util.List;

public interface DockerApi {

    List<Container> listContainers();

    void stopContainer(String containerId, int secondTimeout);

    boolean isContainerRunning(String containerId);

    void startContainer(String containerId);

    ContainerInfo inspectContainer(String containerId);
}
