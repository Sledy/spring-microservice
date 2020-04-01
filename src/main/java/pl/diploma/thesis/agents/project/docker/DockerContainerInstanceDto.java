package pl.diploma.thesis.agents.project.docker;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class DockerContainerInstanceDto {

    private Long id;
    private String containerId;
    private String imageName;
    private String status;
    private LocalDateTime lastStatusUpdate;

}
