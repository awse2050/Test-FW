package com.example.javatest.study;

import com.example.javatest.domain.Member;
import com.example.javatest.domain.Study;
import com.example.javatest.domain.StudyStatus;
import com.example.javatest.member.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

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
class StudyServiceContainerTest {

    @Mock
    private MemberService memberService;

    @Autowired
    private StudyRepository studyRepository;

    // static 를 사용해서 공유하여 쓰도록 한다.
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres")
            .withDatabaseName("studytest");

    // 첫 테스트에 컨테이너를 시작시킨다.
    @BeforeAll
    static void beforeAll() {
        System.out.println("container start!!!");
        postgreSQLContainer.start();
        System.out.println("postgreSQLContainer.getJdbcUrl() : " + postgreSQLContainer.getJdbcUrl());
    }

    // 마지막 테스트 이후에 컨테이너를 종료시킨다.
    @AfterAll
    static void afterAll() {
        System.out.println("container stop!!!");
        postgreSQLContainer.stop();
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
        Study study = new Study("sfsdf",11);
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        service.createNewStudy(1L, study);
        then(memberService).should(times(1)).notify(study);
    }

    @Test
    void createTest2() {
        System.out.println("test....2");
        StudyService service = new StudyService(memberService, studyRepository);
        assertNotNull(service);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("kkkk@naver.com");
        Study study = new Study("sfsdf",11);
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        service.createNewStudy(1L, study);
        then(memberService).should(times(1)).notify(study);
    }
}