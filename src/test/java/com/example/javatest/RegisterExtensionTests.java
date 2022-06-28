package com.example.javatest;

import com.example.javatest.extension.FastSlowTestDeclareExtension;
import com.example.javatest.extension.FastSlowTestRegisterExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

// 테스트 메서드들이 해당 클래스의 한개의 인스턴스를 공유해서 사용한다.
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// 테스트 순서 정하기
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegisterExtensionTests {

    int value = 1;

    // 프로그래밍적 확장
    @RegisterExtension
    private FastSlowTestRegisterExtension fastSlowTestExtension = new FastSlowTestRegisterExtension(1000L);

    @BeforeAll
//    static void beforeAll()
    void beforeAll() {
        System.out.println("beforeAll.....");
    }

    @AfterAll
//  static void afterAll() {
    void afterAll() {
        System.out.println("afterAll.....");
    }

    @Test
    @Order(2)
    void addTest1() {
        System.out.println(value++);
        System.out.println("addTest1");
    }

    @Order(1)
    @Test
    void addTest2() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(value++);
        System.out.println("addTest2");
    }
}
