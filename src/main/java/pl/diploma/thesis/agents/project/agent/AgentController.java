package pl.diploma.thesis.agents.project.agent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.diploma.thesis.agents.project.docker.DockerContainerConfigDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceDto;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
class AgentController {

    private final DockerContainerInstanceService dockerContainerInstanceService;

    @GetMapping("/sendok")
    public String sendOk() {
        return "OK";
    }

    @PostMapping(value = "container", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DockerContainerInstanceDto> createDockerContainer(@RequestBody DockerContainerConfigDto dockerContainerConfigDto) {
        dockerContainerInstanceService.createDockerContainer(dockerContainerConfigDto);
        return null;
    }


    @GetMapping(value = "/container", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DockerContainerInstanceDto>> getAllContainers() {
        List<DockerContainerInstanceDto> dtoList = dockerContainerInstanceService.listAllContainers();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/container/{containerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContainer(@PathVariable("containerId") String containerId) {
        dockerContainerInstanceService.deleteContainer(containerId);
        return new ResponseEntity<>(containerId, HttpStatus.ACCEPTED);
    }
}
