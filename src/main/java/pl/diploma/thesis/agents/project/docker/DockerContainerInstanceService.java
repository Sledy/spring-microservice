package pl.diploma.thesis.agents.project.docker;

import java.util.List;

public interface DockerContainerInstanceService {

    void saveDockerInstanceInfo(DockerContainerInstanceDto containerInstanceDto);

    void saveAllDockerInstancesInfo(List<DockerContainerInstanceDto> containerInstanceDtoListList);

    boolean updateAllDockerInstancesInfo();

    boolean removeContainer(DockerContainerInstanceDto dockerContainerInstanceDto);

    boolean purgeRemovedContainerFromDB(DockerContainerInstanceDto dockerContainerInstanceDto);

    boolean deleteContainer(String containerId);

    boolean stopContainer(DockerContainerInstanceDto dockerContainerInstanceDto);

}
