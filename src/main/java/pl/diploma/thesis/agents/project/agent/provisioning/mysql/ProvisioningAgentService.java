package pl.diploma.thesis.agents.project.agent.provisioning.mysql;

import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;

public interface ProvisioningAgentService {

    void provisionMySqlDatabase(DockerContainerConfigDto dockerContainerConfigDto);

}
