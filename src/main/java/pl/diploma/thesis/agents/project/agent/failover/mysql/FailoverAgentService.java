package pl.diploma.thesis.agents.project.agent.failover.mysql;

import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

public interface FailoverAgentService {

    void executeDatabaseInstanceFailover(DockerContainerInstanceDto dockerContainerInstanceDto,
                                         DockerContainerConfigDto dockerContainerConfigDto);

}
