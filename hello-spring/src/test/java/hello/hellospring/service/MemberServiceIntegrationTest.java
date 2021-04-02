package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    // 서비스에서 만든 그 레포지토리를 가지고 테스트를 해야지, 여기서 new로 만들어서 하면 다른 레파지토리로 테스트 하는 것.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given 이런 상황이 주어져서
        Member member = new Member();
        member.setName("hello");

        //When  이걸 실행했을 때
        Long saveId = memberService.join(member);

        //Then  결과가 이게 나와야 해
        Member findMember = memberService.findOne(saveId).get();
//        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    // 테스트는 단순하게 정상작동 되는지 테스트 하는 것도 중요하지만,
    // 예외 처리 되는 부분이 터지는지 테스트 해보는 것도 중요하므로 예외 과정을 테스트 해보자.
    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}