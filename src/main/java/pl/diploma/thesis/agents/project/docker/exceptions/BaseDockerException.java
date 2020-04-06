package pl.diploma.thesis.agents.project.docker.exceptions;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
abstract class BaseDockerException extends RuntimeException {

    protected BaseDockerException(String msg){
        log.error(msg);
    }

    protected BaseDockerException(Throwable cause) {
        super(cause);
        log.error(cause.getMessage());
        log.error(Arrays.toString(cause.getStackTrace()));
    }

    protected BaseDockerException(String msg, Throwable cause){
        super(cause);
        log.error(msg);
        log.error(cause.getMessage());
        log.error(Arrays.toString(cause.getStackTrace()));
    }

}
