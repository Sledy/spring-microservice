package pl.diploma.thesis.agents.project.agent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.diploma.thesis.agents.project.docker.DockerContainerInstanceService;

@RestController
@RequiredArgsConstructor
class AgentController {

    private final DockerContainerInstanceService dockerContainerInstanceService;

    @GetMapping("/sendok")
    public String sendOk(){
        return "OK";
    }

    @DeleteMapping(value = "/container/{containerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContainer(@PathVariable("containerId") String containerId){
        dockerContainerInstanceService.deleteContainer(containerId);
        return new ResponseEntity<>(containerId, HttpStatus.ACCEPTED);
    }
}
