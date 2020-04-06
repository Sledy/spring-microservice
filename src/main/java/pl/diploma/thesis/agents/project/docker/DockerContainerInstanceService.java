package pl.diploma.thesis.agents.project.docker;

import java.util.List;

public interface DockerContainerInstanceService {

    void saveDockerInstanceInfo(DockerContainerInstanceDto containerInstanceDto);

    void updateAllDockerInstancesInfo();

    void removeContainer(DockerContainerInstanceDto dockerContainerInstanceDto);

    void purgeRemovedContainerFromDB(DockerContainerInstanceDto dockerContainerInstanceDto);

    void deleteContainer(String containerId);

    void stopContainer(DockerContainerInstanceDto dockerContainerInstanceDto);

    List<DockerContainerInstanceDto> listAllContainers();

    void createDockerContainer(DockerContainerConfigDto dockerContainerConfigDto);

}
