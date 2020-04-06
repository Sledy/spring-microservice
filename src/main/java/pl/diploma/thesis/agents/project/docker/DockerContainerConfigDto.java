package pl.diploma.thesis.agents.project.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class DockerContainerConfigDto {

    @JsonProperty("image")
    String image;
    @JsonProperty("commandList")
    List<String> commandList = new LinkedList<>();
    @JsonProperty("envList")
    List<String> envList = new ArrayList<>();
    @JsonProperty("exposedPortsList")
    Set<String> exposedPortsList;

}
