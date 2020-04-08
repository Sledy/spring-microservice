package pl.diploma.thesis.agents.project.agent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.diploma.thesis.agents.project.agent.events.EventPublisher;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
class AgentController {

    private final DockerContainerInstanceService dockerContainerInstanceService;
    private final EventPublisher eventPublisher;

    @PostMapping(value = "container", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> provisionMySqlDatabase(@RequestBody DockerContainerConfigDto dockerContainerConfigDto) {
        Long id = dockerContainerInstanceService.registerContainerInDb(dockerContainerConfigDto);
        eventPublisher.publishMySqlProvisioningEvent(dockerContainerConfigDto);
        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/container", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DockerContainerInstanceDto>> getAllContainers() {
        List<DockerContainerInstanceDto> dtoList = dockerContainerInstanceService.listAllContainers();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/container/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContainer(@PathVariable("Id") String id) {
        dockerContainerInstanceService.deleteContainer(id);
        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }
}
