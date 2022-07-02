package com.example.javatest.study;

import com.example.javatest.domain.Member;
import com.example.javatest.domain.Study;
import com.example.javatest.domain.StudyStatus;
import com.example.javatest.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/*
    Mocking 테스트
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class) // @Mock 사용시 필요한 확장
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = StudyServiceContainerTest.PropertyInitializer.class)
class StudyServiceContainerTest {

    @Mock
    private MemberService memberService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private Environment environment;

    // static 를 사용해서 공유하여 쓰도록 한다.
    @Container // start, stop을 대신 하여 컨테이너의 라이프사이클 관리.
    private static GenericContainer genericContainer =
            new GenericContainer("postgres")
            .withEnv(Map.of("POSTGRES_DB", "studytest",
                    "POSTGRES_PASSWORD", "studytest"))
            .withExposedPorts(5432);

    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer slf4jLogConsumer = new Slf4jLogConsumer(log);
        genericContainer.followOutput(slf4jLogConsumer);
    }

    @BeforeEach
    void repositoryClearBeforeTest() {
        System.out.println("==============");
        System.out.println("getPort : " + genericContainer.getMappedPort(5432));
        studyRepository.deleteAll();
        System.out.println(environment.getProperty("container.port"));
        System.out.println("clear!!!");
    }

    /*
        @Mock 을 통해 만들어진 Mock 객체를 특정 대상에 의존성으로 주입해서 사용할 경우
        @InjectMocks 를 선언하고 주입될 대상을 적어둔다.
     */
    @Test
    void createTest() {
        System.out.println("test....");
        StudyService service = new StudyService(memberService, studyRepository);
        assertNotNull(service);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("kkkk@naver.com");
        Study study = new Study("sfsdf", 11);
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        service.createNewStudy(1L, study);
        then(memberService).should(times(1)).notify(study);
    }

    @Test
    void createTest2() {
        System.out.println("test....");
        StudyService service = new StudyService(memberService, studyRepository);
        assertNotNull(service);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("kkkk@naver.com");
        Study study = new Study("sfsdf", 11);
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        service.createNewStudy(1L, study);
        then(memberService).should(times(1)).notify(study);
    }

    // 스프링 테스트에서 참조하기
    static class PropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            // 테스트용 프로퍼티를 정의하는 코드
            TestPropertyValues.of("container.port="+genericContainer.getMappedPort(5432))
                    .applyTo(applicationContext.getEnvironment());
        }
    }
}