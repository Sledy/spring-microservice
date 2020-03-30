package pl.diploma.thesis.agents.project.docker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "docker_instances")
@Data
@NoArgsConstructor()
class DockerContainerInstance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String containerId;
    private String imageName;
    private String status;
    private LocalDateTime lastStatusUpdate;
}
