package com.project.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Inventroy Service")
                .description("재고 관리 서비스입니다.").build();
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.project.inventoryservice.api"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

}
