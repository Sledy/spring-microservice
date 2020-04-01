package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.ContainerInfo;

import java.util.List;

public interface DockerContainerInstanceService {

    void saveDockerInstanceInfo(ContainerInfo containerInfo);

    void saveAllDockerInstancesInfo(List<ContainerInfo> containerInfoList);

    boolean updateAllDockerInstancesInfo();

}
