package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)    //Junit 실행할 때 spring 이랑 엮어서 실행할래.
@SpringBootTest     //없으면 Autowired 다 실패 함.
@Transactional  //test 케이스에 있으면 기본적으로 rollback
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(false)    //눈으로 확인하고 싶으니 rollback 하지마라는 의미.
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
//        em.flush(); //영속성을 db 에 반영
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);    //예외가 발생해야 한다.


        //then
        fail("예외가 발생해야 한다.");
    }
}