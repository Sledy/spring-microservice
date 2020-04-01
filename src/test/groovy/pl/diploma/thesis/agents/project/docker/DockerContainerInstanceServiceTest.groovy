package pl.diploma.thesis.agents.project.docker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = DockerContainerInstanceService)
@Import(DockerTestConfiguration)
class DockerContainerInstanceServiceTest extends Specification{

    @Autowired
    private DockerContainerInstanceService dockerContainerInstanceService
    @Autowired
    private DockerContainerInstanceRepository testDockerContainerRepository

    def "should updateAllDockerInstanceInfo correctly load docker instances and map to DockerContainerInstanceDto"(){
        given:
        testDockerContainerRepository.findDockerContainerInstanceByContainerId(_ as String) >> getDockerContainerInstance()
        when:
        def result = dockerContainerInstanceService.updateAllDockerInstancesInfo()
        then:
        result
    }

    def private static getDockerContainerInstance(){
        DockerContainerInstance dockerContainerInstance = new DockerContainerInstance()
        dockerContainerInstance.setContainerId("6969696")
        dockerContainerInstance.setImageName("imageName")
        dockerContainerInstance.setLastStatusUpdate(LocalDateTime.now())
        dockerContainerInstance.setStatus("running")
        return dockerContainerInstance
    }

}
