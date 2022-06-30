package com.example.javatest;

import com.example.javatest.domain.Study;
import com.example.javatest.domain.StudyStatus;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/*
    22.06.27
    public 을 안붙여도 사용이 가능하다.
    리플렉션이라는 기술을 사용하고 있기 때문이다.

    XXXAll 애노테이션은 static 을 붙여야 한다.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기 1")
        // 우선순위가 조금 더 높다.
    void create1() {
        /*
             테스트가 중간에 실패할 경우 이후 테스트에 대한 실행이 안된다
             따라서 assertAll 을 사용해서 이후 테스트에 대한 검증이 그대로 가능하다.
         */
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        // 문자열에 중간에 연산이 들어갈 경우 람다식을 쓰는 것이 더 좋음.
                        // 문자열만 사용해서 메세지를 나타낼 수 도 있다.
                        () -> "스터디가 생성되면 DRAFT 여야 한다."),
                () -> assertTrue(1 < 2)
        );
        System.out.println("스터디 만들기 1");
    }

    @Test
    @DisplayName("스터디 생성시 예외 발생 테스트")
    void create2() {

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> new Study(9));
        String message = ex.getMessage();

        assertEquals("값이 10 이하 입니다.", message);

        System.out.println("스터디 만들기 2");
    }

    @Test
    @DisplayName("스터디 생성시 생성 시간 테스트")
    void create3() {
        // assertTimeoutPreemptively 로 블럭이 끝나기 전에
        // 특정 시간이 지날때쯤 강제로 끝 낼 수 있지만, 쓰레드 를 사용하는 곳에서는 사용하지 않는게 좋다.
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            // sleep을 걸면 해당 블럭이 끝날 때까지 시간을 기다리기 떄문에 실패가 나옴.
//            Thread.sleep(300);
        });
        System.out.println("스터디 만들기 3");
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
        System.out.println();
    }
}