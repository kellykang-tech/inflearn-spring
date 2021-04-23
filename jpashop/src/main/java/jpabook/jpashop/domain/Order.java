package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //db 하시는 분들이 이렇게 name 짓는 걸 선호
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") //foreign key 설정. 연관관계 주인을 정해줌
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   //persist 를 전파해준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
    * OneToOne 은 둘 중 아무데나 FK 줘도 되는데, 자주 조회하는 쪽에 두는 편이 낫다.
    * Order 를 연관관계 주인으로 설정.
     */
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드==// 양방향에서는 편의 메서드가 있으면 좋다. 양방향이 딱딱 세팅.

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //setMember(Member member) == 이 로직
//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//
//        member.getOrders().add(order);
//        order.setMember(member);
//    }

}
