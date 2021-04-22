package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //db 하시는 분들이 이렇게 name 짓는 걸 선호
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //foreign key 설정. 연관관계 주인을 정해줌
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
    * OneToOne 은 둘 중 아무데나 FK 줘도 되는데, 자주 조회하는 쪽에 두는 편이 낫다.
    * Order 를 연관관계 주인으로 설정.
     */
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

}
