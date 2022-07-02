package com.example.javatest.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Component
@Testcontainers
public class CustomTestContainer {
    // static 를 사용해서 공유하여 쓰도록 한다.
    @Container // start, stop을 대신 하여 컨테이너의 라이프사이클 관리.
    @Autowired
    private PostgreSQLContainer postgreSQLContainer;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PostgreSQLContainer postgreSQLContainer() {
            return new PostgreSQLContainer("postgres")
                    .withDatabaseName("studytest");
        }
    }
}
