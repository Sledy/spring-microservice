package pl.diploma.thesis.agents.project.docker;

import org.springframework.data.repository.CrudRepository;

interface DockerContainerInstanceRepository extends CrudRepository<DockerContainerInstance, Long> {
}
