package pl.diploma.thesis.agents.project.mysql;

import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;

public interface MySqlService {

    boolean mySqlDaemonIsRunning(DockerContainerInstanceDto dockerContainerInstanceDto);

}
