package pl.diploma.thesis.agents.project.docker.exceptions;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DockerInterruptedException extends BaseDockerException{

    public DockerInterruptedException(Throwable cause) {
        super(cause);
    }
}
