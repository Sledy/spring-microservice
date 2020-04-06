package pl.diploma.thesis.agents.project.docker.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnexpectedDockerResponseException extends BaseDockerException {

    public UnexpectedDockerResponseException(Throwable cause) {
        super(cause);
    }
}
