package com.example.javatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/*
    22.06.27
    public 을 안붙여도 사용이 가능하다.
    리플렉션이라는 기술을 사용하고 있기 때문이다.

    XXXAll 애노테이션은 static 을 붙여야 한다.
 */
class StudyTest {

    @Test
    void create1() {
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    void create2() {
        Study study = new Study();
        assertNotNull(study);
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all ...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all...");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each...");
    }
}