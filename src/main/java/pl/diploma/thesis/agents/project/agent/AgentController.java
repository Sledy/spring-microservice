package pl.diploma.thesis.agents.project.agent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AgentController {
    @GetMapping("/sendok")
    public String sendOk(){
        return "OK";
    }
}
