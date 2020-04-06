package pl.diploma.thesis.agents.project.docker.exceptions;

public class ContainerCreationException extends BaseDockerException {

    public ContainerCreationException(String msg) {
        super(msg);
    }

    public ContainerCreationException(Throwable cause) {
        super(cause);
    }

    public ContainerCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
