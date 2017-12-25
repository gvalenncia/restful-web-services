package com.training.rws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Restful api documentation",
            "This is the restful api documentation",
            "V1.0",
            "",
            new Contact("German", "none", "tales@tales.com"),
            "apache licence",
            "none"
    );
    private static final Set<String> DEFAULT_PRODUCES_CONSUMES =
            new HashSet<>(Arrays.asList("application/json", "application/xml"));

    @Bean(name = "api")
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_CONSUMES)
                .consumes(DEFAULT_PRODUCES_CONSUMES);
    }
}
