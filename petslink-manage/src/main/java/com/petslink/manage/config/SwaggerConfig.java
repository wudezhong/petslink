package com.petslink.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 创建一个Docket对象
     * 调用select()方法，
     * 生成ApiSelectorBuilder对象实例，该对象负责定义外漏的API入口
     * 通过使用RequestHandlerSelectors和PathSelectors来提供Predicate，在此我们使用any()方法，将所有API都通过Swagger进行文档管理
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.petslink.manage"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        //标题
                        .title("HotDog-Api文档")
                        //简介
                        .description("HotDog项目")
                        //版本
                        .version("9.0")
                        //作者个人信息
                        .contact(new Contact("HotDog开发团队", "https://www.baidu.com", "3040888382@qq.com"))
//                        .license("The Apache License")
                        //服务条款
//                        .licenseUrl("https://gblfy.com")
                        .build());
    }

}

