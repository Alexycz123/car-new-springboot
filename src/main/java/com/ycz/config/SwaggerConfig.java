package com.ycz.config;/*
 @author ycz
 @date 2021-10-09-16:43
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//开启swagger2
@EnableSwagger2
public class SwaggerConfig {


    //配置了swagger的Bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ycz.controller"))
                .build()
                ;
    }


    //配置swagger信息
    private ApiInfo apiInfo(){
        return new ApiInfo(("Ycz Documentation"),
                "ycz Documentation",
                "1.0",
                "urn:tos",
               "Contact Email",
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0");
    }


}
