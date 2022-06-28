package com.example.javatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RepeatedTests {
    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    // RepetitionInfo 를 인자로 받아서 몇번 쨰 인지, 총 몇번인지 알수 있다.
    void create1(RepetitionInfo repetitionInfo) {
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertTrue(1 < 2)
        );
        System.out.println("횟수 : " + repetitionInfo.getCurrentRepetition() + "/" +repetitionInfo.getTotalRepetitions());
        System.out.println("스터디 만들기 1");
    }

    // 사용가능한 대체문법
    // index, arguments, displayName, 0, 1
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index}, {displayName} message={0} ")
    @ValueSource(strings = {"날씨가", "많이", "춥네요"})
    void parameterTests(String message) {
        System.out.println("message.. : "+ message + "/" );
    }
}
