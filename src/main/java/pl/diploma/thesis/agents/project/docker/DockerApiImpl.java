package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.diploma.thesis.agents.project.docker.exceptions.DockerInterruptedException;
import pl.diploma.thesis.agents.project.docker.exceptions.UnexpectedDockerResponseException;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DockerApiImpl implements DockerApi {

    private final DockerClient dockerClient;

    @Override
    public List<Container> listContainers() {
        try {
            return dockerClient.listContainers();
        } catch (DockerException e) {
            throw new UnexpectedDockerResponseException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }

    @Override
    public void stopContainer(String containerId, int secondTimeout) {
        try {
            dockerClient.stopContainer(containerId, secondTimeout);
        } catch (DockerException e) {
            throw new UnexpectedDockerResponseException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }

    @Override
    public boolean isContainerRunning(String containerId) {
        try {
            return dockerClient.inspectContainer(containerId).state().running();
        } catch (DockerException e) {
            throw new UnexpectedDockerResponseException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }

    @Override
    public void startContainer(String containerId) {
        try {
            dockerClient.startContainer(containerId);
        } catch (DockerException e) {
            throw new UnexpectedDockerResponseException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }

    @Override
    public ContainerInfo inspectContainer(String containerId) {
        try {
            return dockerClient.inspectContainer(containerId);
        } catch (DockerException e) {
            throw new UnexpectedDockerResponseException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }
}
