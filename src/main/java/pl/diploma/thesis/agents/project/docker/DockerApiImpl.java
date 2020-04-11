package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.diploma.thesis.agents.project.docker.exceptions.ContainerCreationException;
import pl.diploma.thesis.agents.project.docker.exceptions.DockerInterruptedException;
import pl.diploma.thesis.agents.project.docker.exceptions.UnexpectedDockerResponseException;
import pl.diploma.thesis.agents.project.utils.ExceptionFormatter;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
class DockerApiImpl implements DockerApi {

    private final DockerClient dockerClient;
    private final ExceptionFormatter exceptionFormatter;

    @Override
    public List<Container> listContainers() {
        try {
            return dockerClient.listContainers(DockerClient.ListContainersParam.allContainers());
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
    public void removeContainer(String containerId) {
        try {
            dockerClient.removeContainer(containerId);
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

    @Override
    public ContainerConfig buildContainerConfig(DockerContainerConfigDto dockerContainerConfigDto) {
        return ContainerConfig.builder()
                .image(dockerContainerConfigDto.getImage())
                .exposedPorts(dockerContainerConfigDto.getExposedPortsList())
                .cmd(dockerContainerConfigDto.getCommandList())
                .env(dockerContainerConfigDto.getEnvList())
                .build();
    }

    @Override
    public ContainerCreation createContainer(ContainerConfig containerConfig) {
        try {
            return dockerClient.createContainer(containerConfig);
        } catch (DockerException e) {
            String msg = "Cannot create container";
            throw new ContainerCreationException(msg, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }

    @Override
    public Optional<TopResults> listContainerProcesses(DockerContainerInstanceDto dockerContainerInstanceDto) {
        try {
            return Optional.of(dockerClient.topContainer(dockerContainerInstanceDto.getContainerId(), "-aux"));
        } catch (DockerException e) {
            log.error("Error during container processes check.");
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DockerInterruptedException(e);
        }
    }
}
