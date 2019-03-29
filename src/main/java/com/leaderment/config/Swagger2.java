package com.leaderment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableWebMvc
public class Swagger2 {

   @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("register")
        		.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.select().apis(RequestHandlerSelectors.basePackage("com.leaderment.register.handler"))
				.paths(PathSelectors.any()).build().apiInfo(demoInfo());
    }
    @Bean
    public Docket testApi() {
    	 return new Docket(DocumentationType.SWAGGER_2).groupName("claim")
         		.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
 				.select().apis(RequestHandlerSelectors.basePackage("com.leaderment.claim.handler"))
 				.paths(PathSelectors.any()).build().apiInfo(testInfo());
    }

    private ApiInfo demoInfo() {
        return new ApiInfoBuilder()
                .title("warranty RESTful APIs")
                .description("warranty RESTful APIs")
                .termsOfServiceUrl("http://www.honsen.com/")
                .contact("Honsen Lee")
                .version("1.0")
                .build();
    }
    private ApiInfo testInfo() {
        return new ApiInfoBuilder()
                .title("claim模块")
                .version("1.0")
                .build();
    }
}