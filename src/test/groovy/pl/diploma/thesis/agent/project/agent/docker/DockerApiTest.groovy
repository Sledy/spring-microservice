package pl.diploma.thesis.agent.project.agent.docker

import com.spotify.docker.client.DefaultDockerClient
import com.spotify.docker.client.DockerClient
import com.spotify.docker.client.messages.Version
import spock.lang.Specification

class DockerApiTest extends Specification{

    def "shit"(){
        given:
        DockerClient client = DefaultDockerClient.fromEnv().build()
        when:
        Version version = client.version()
        then:
        version != null
    }


}
