package com.example.javatest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedTests {

    // 객체를 변환하는 Converter를 구현해야 한다.
    // 이떄 사용하는 Converter는 SimpleArgumentConverter
    @DisplayName("ValueSource 값 객체 변환 테스트")
    @ParameterizedTest(name = "{index}, {displayName} message={0}")
    @ValueSource(ints = {10,20,40})
    void valueSourceToConvertObjectTests(Integer limit) {
        System.out.println("limit : " + limit);
        System.out.println("new Study " + new Study(limit));
    }

    @DisplayName("CsvSource 값 객체 변환 테스트")
    @ParameterizedTest(name = "{index}, {displayName} message={0}")
    @CsvSource({"10", "20", "40"})
    void csvSourceToConvertObjectTest(@ConvertWith(StudyArgumentConverter.class) Study study) {
        System.out.println("study : " + study);
    }

    // 단일 매개변수를 받아서 변환할 떄 사용한다.
    static class StudyArgumentConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "Study 클래스여야 합니다.");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    @DisplayName("CsvSource 값 객체 직접 변환 테스트")
    @ParameterizedTest(name = "{index}, {displayName} message={0}")
    @CsvSource({"10, 자바", "20, 스프링", "40, PHP"})
    void csvSourceToAggregateObjectTest(Integer limit, String name) {
        System.out.println("limit : "+ limit + ", name : " + name);
        System.out.println("new Study ... : " + new Study(name, limit));
    }

    @DisplayName("CsvSource 값 객체 직접 변환 테스트")
    @ParameterizedTest(name = "{index}, {displayName} message={0}")
    @CsvSource({"10, 자바", "20, 스프링", "40, PHP"})
    void csvSourceToAggregateObjectTest(ArgumentsAccessor accessor) {
        System.out.println("new Study ... : " + new Study(accessor.getString(1), accessor.getInteger(0)));
    }

    @DisplayName("CsvSource 값 Aggregator를 통한 변환 테스트")
    @ParameterizedTest(name = "{index}, {displayName} message={0}")
    @CsvSource({"10, 자바", "20, 스프링", "40, PHP"})
    void csvSourceToAggregateObjectTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println("study : " + study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getString(1), argumentsAccessor.getInteger(0));
        }
    }
}
