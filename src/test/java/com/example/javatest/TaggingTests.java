package com.example.javatest;

import com.example.javatest.annotation.FastTest;
import com.example.javatest.annotation.SlowTest;
import com.example.javatest.domain.Study;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaggingTests {

    @FastTest
    @DisplayName("스터디 만들기 1")
    void create1() {
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertTrue(1 < 2)
        );
        System.out.println("스터디 만들기 1");
    }

    @SlowTest
    @DisplayName("스터디 만들기 2")
    void create2() {
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertTrue(1 < 2)
        );
        System.out.println("스터디 만들기 2");
    }
}
