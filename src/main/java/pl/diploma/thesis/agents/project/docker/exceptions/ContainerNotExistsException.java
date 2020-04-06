package pl.diploma.thesis.agents.project.docker.exceptions;

public class ContainerNotExistsException extends BaseDockerException {

    public ContainerNotExistsException(String msg) {
        super(msg);
    }

    public ContainerNotExistsException(Throwable cause) {
        super(cause);
    }
}
