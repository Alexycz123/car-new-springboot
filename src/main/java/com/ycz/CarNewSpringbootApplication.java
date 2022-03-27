package com.ycz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CarNewSpringbootApplication {

    private static final Logger logger = LoggerFactory.getLogger(CarNewSpringbootApplication.class);

    public static void main(String[] args) {
        logger.info("===============项目启动了===============");
        SpringApplication.run(CarNewSpringbootApplication.class, args);
        logger.info("===============启动成功了===============");

    }

}
