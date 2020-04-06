package pl.diploma.thesis.agents.project.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@EnableSwagger2WebMvc
@Configuration
class SpringFoxConfiguration {
    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("pl.diploma.thesis.agents.project.swagger")
                        .or(RequestHandlerSelectors.basePackage("pl.diploma.thesis.agents.project")))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }
}
