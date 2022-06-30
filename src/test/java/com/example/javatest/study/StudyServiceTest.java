package com.example.javatest.study;

import com.example.javatest.domain.Member;
import com.example.javatest.domain.Study;
import com.example.javatest.domain.StudyStatus;
import com.example.javatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/*
    Mocking 테스트
 */
@ExtendWith(MockitoExtension.class) // @Mock 사용시 필요한 확장
class StudyServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private StudyRepository studyRepository;

    /*
        @Mock 을 통해 만들어진 Mock 객체를 특정 대상에 의존성으로 주입해서 사용할 경우
        @InjectMocks 를 선언하고 주입될 대상을 적어둔다.
     */
    @InjectMocks
    private StudyService studyService;

    @Test
    void createStudyService() {
        /*
            주입해야 하는 객체가 없다. 구현체가 없어서 넣어도 동작을 테스트하기 어렵다
            따라서 Mocking을 통해 가짜 객체를 만들어내고 Stubbing을 해야한다.
         */
        // @InjectMocks 대체
//        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("wesaq@naver.com");

        Study study = new Study("하이", 10);
        // 특정 상황에서 예상하는 결과가 나오도록 행동에 대한 결과를 Stubbing 한다.
        // anyLong() -> 어떤 Long 타입 값을 넣어도 해당 결과가 나오게 한다.
        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(2L, study);

//        assertNotNull(study.getOwnerId());
        verify(memberService, times(1)).notify(member);
        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(anyLong());
        verifyNoMoreInteractions(memberService);
        verify(studyRepository, times(1)).save(study);

        // 순서대로 동작하였는지 체크.
        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(studyRepository, times(1)).save(study); // Mock객체를 넣지 않았으므로 에러
        inOrder.verify(memberService, times(1)).notify(study);
        inOrder.verify(memberService, times(1)).notify(member);
    }

    @DisplayName("BDD API 활용")
    @Test
    void createStudyServiceOnBDD() {
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("wesaq@naver.com");

        Study study = new Study("하이", 10);
        given(memberService.findById(anyLong())).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        studyService.createNewStudy(2L, study);

        then(memberService).should().notify(study);
        then(memberService).should().notify(member);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void bDDPractice() {
        Study study = new Study("하이", 10);
        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        given(studyRepository.save(study)).willReturn(study);
        // When
        studyService.openStudy(study);
        // Then
        // TODO study의 status가 OPENED로 변경됐는지 확인
        assertEquals(StudyStatus.OPENED, study.getStatus());
        // TODO study의 openedDataTime이 null이 아닌지 확인
        assertNotNull(study.getOpenedDateTime());
        // TODO memberService의 notify(study)가 호출 됐는지 확인.
        then(memberService).should().notify(study);
    }
}