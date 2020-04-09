package pl.diploma.thesis.agents.project.mysql;

import lombok.RequiredArgsConstructor;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

@RequiredArgsConstructor
class MySqlServiceImpl implements MySqlService {

    private final DockerContainerInstanceService dockerContainerInstanceService;

    @Override
    public boolean mySqlDaemonIsRunning(DockerContainerInstanceDto dockerContainerInstanceDto) {
        return dockerContainerInstanceService.getContainerProcessesList(dockerContainerInstanceDto)
                                      .stream()
                                      .anyMatch(process -> process.equals("mysqld"));
    }
}
