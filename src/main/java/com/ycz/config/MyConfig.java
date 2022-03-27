package com.ycz.config;/*
 @author ycz
 @date 2021-09-27-18:20
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    /**
     * 国际化
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

//    @Bean
////    public LocaleResolver localeResolver2(){
////        return new MyLocaleResolver2();
////    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
//        registry.addViewController("/main").setViewName("main");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
                .allowedOriginPatterns("*")            //开放哪些ip、端口、域名的访问权限
                .allowedMethods( "GET", "POST", "PUT", "OPTIONS", "DELETE")        //开放哪些Http方法，允许跨域访问
                .allowCredentials(true)         //是否允许发送Cookie信息
                .maxAge(3600)
                .allowedHeaders("*");            //允许HTTP请求中的携带哪些Header信息
    }
}
