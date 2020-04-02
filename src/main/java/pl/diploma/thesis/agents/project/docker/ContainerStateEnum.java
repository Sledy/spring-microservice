package pl.diploma.thesis.agents.project.docker;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ContainerStateEnum {

    RUNNING("running"),
    RESTARTING("restarting"),
    CREATED("created"),
    PAUSED("paused"),
    EXITED("exited"),
    DEAD("dead"),
    UNKNOWN("unknown");


    private static final Map<String, ContainerStateEnum> enumMap;
    private String state;

    static {
        enumMap = Stream.of(ContainerStateEnum.values())
                .collect(Collectors.toMap(ContainerStateEnum::getValue, containerStateEnum -> containerStateEnum));
    }

    ContainerStateEnum(String state) {
        this.state = state;
    }

    public String getValue() {
        return this.state;
    }

    public static ContainerStateEnum fromValue(String stateString) {
        boolean stateExists = Stream.of(ContainerStateEnum.values())
                .map(ContainerStateEnum::getValue)
                .anyMatch(value -> value.equals(stateString));
        if (stateExists) {
            return enumMap.get(stateString);
        }
        return ContainerStateEnum.UNKNOWN;
    }

}
