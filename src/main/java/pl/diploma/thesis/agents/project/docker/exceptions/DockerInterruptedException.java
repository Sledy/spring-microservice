package pl.diploma.thesis.agents.project.docker.exceptions;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class DockerInterruptedException extends RuntimeException{
    public DockerInterruptedException(Throwable cause) {
        super(cause);
        log.error(cause.getMessage());
        log.error(Arrays.toString(cause.getStackTrace()));
    }
}
