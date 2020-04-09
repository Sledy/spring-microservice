package pl.diploma.thesis.agents.project.docker

import com.fasterxml.jackson.databind.ObjectMapper
import com.spotify.docker.client.messages.TopResults
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import pl.diploma.thesis.agents.project.utils.JsonConverter
import spock.lang.Specification

@SpringBootTest(classes = [DockerApiImpl])
@Import([DockerTestConfiguration])
class DockerApiTest extends Specification {

    @Autowired
    private DockerApi testDockerApi
    @Autowired
    private JsonConverter testJsonConverter

    def "should return ContainerConfig"() {
        given:
        String filePath = "/home/adam/IdeaProjects/agent-compute/src/test/resources/docker/dockerContainerConfiguration.json"
        String json = new File(filePath).text
        and:
        def dto = testJsonConverter.convertToObject(json, DockerContainerConfigDto).get()
        when:
        def config = testDockerApi.buildContainerConfig(dto as DockerContainerConfigDto)
        then:
        with(config){
            it.image() == dto.image
            it.env().intersect(dto.envList)
            it.exposedPorts().intersect(dto.exposedPortsList)
            it.cmd().intersect(dto.commandList)
        }
    }

    def "should return container processes list"(){
        given:
        DockerContainerInstanceDto dockerContainerInstanceDto = new DockerContainerInstanceDto()
        dockerContainerInstanceDto.setContainerId("e9cdfe3c922b")
        when:
        def processes = testDockerApi.listContainerProcesses(dockerContainerInstanceDto)
        then:
        processes != null

    }



    def "should read from json"(){
        given:
        String filePath = "/home/adam/IdeaProjects/agent-compute/src/test/resources/docker/dockerContainerConfiguration.json"
        when:
        String json = new File(filePath).text
        def dockerContainerConfigDto = testJsonConverter.convertToObject(json, DockerContainerConfigDto)
        then:
        dockerContainerConfigDto != null
    }


}
