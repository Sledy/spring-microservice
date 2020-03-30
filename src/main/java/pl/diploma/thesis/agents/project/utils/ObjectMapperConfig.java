package pl.diploma.thesis.agents.project.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ObjectMapperConfig {

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
