package pl.diploma.thesis.agents.project.docker;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "DOCKER_INSTANCES")
@Data
@NoArgsConstructor()
class DockerContainerInstance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String containerName;
    private String containerId;
    private String imageName;
    private String status;
    @LastModifiedDate
    private LocalDateTime lastStatusUpdate;
}
