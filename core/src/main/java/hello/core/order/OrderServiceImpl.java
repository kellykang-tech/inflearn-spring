package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
//@RequiredArgsConstructor    //롬복 고마워
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;    //final: 얘는 있어야 해! (필수값에 사용). 생성자에서만 값을 넣어줄 수 있다.
    private final DiscountPolicy discountPolicy;

//    @Autowired(required = false)    //주입할 대상이 없어도 동작하게 하려면
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;   //공용필드에 final 붙여서 쓰면 여기에 오류가 뜸. 왜지?
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

    //new OrderServiceImpl(memberRepository, discountPolicy)    스프링도 이렇게 만듦
    @Autowired  //생성자가 딱 1개 있으면 @Autowired 생략해도 됨
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {     //생성자 주입 방법을 쓰면 멤버필드에 final 키워드를 사용할 수 있다.
        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }


    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
