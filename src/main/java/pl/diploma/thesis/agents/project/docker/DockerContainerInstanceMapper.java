package pl.diploma.thesis.agents.project.docker;

import com.spotify.docker.client.messages.ContainerInfo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Mapper
interface DockerContainerInstanceMapper { //NOSONAR

    DockerContainerInstanceMapper INSTANCE = Mappers.getMapper(DockerContainerInstanceMapper.class);

    @Mapping(ignore = true, target = "id")
    DockerContainerInstance mapToDockerContainerInstance(DockerContainerInstanceDto dockerContainerInstanceDto);

    @InheritInverseConfiguration(name = "mapToDockerContainerInstance")
    DockerContainerInstanceDto mapToDockerContainerInstanceDto(DockerContainerInstance dockerContainerInstance);

    default DockerContainerInstanceDto mapContainerInfoToDockerInstanceDto(ContainerInfo containerInfo){
        if(containerInfo == null){
            return null;
        }
        DockerContainerInstanceDto dto = new DockerContainerInstanceDto();
        dto.setContainerId(containerInfo.id());
        dto.setImageName(containerInfo.image());
        dto.setStatus(containerInfo.state().status());
        return dto;
    }
}
