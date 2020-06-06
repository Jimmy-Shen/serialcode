package com.demo.serialcode;

import com.demo.serialcode.config.RedisTemplateConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * main
 *
 * @author shenhongjun
 * @since 2020/5/12
 */
@SpringBootApplication(scanBasePackages = "com.demo")
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class DemoServiceApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(RedisTemplateConfig.class, args);
    }

    @Test
    public void contextLoads() {
    }
}
