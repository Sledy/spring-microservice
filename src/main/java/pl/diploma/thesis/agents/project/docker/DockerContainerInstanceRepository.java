package pl.diploma.thesis.agents.project.docker;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface DockerContainerInstanceRepository extends CrudRepository<DockerContainerInstance, Long> {

    Optional<DockerContainerInstance> findDockerContainerInstanceByContainerId(String containerId);

}
