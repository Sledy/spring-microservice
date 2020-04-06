package pl.diploma.thesis.agents.project.agent.provisioning.mysql;

import lombok.RequiredArgsConstructor;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

@RequiredArgsConstructor
class ProvisioningAgentServiceImpl implements ProvisioningAgentService {

    private final DockerContainerInstanceService dockerContainerInstanceService;

    @Override
    public boolean provisionMySQLDatabase() {

        return false;
    }

}
