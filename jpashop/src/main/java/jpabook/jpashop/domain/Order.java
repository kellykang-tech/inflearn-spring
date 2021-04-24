package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
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

    //==생성 메서드==/
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직==//

    /**
     * 주문 취소: 재고량이 변해야 한다.
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {      //COMP: 배송완료
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     *
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

//    public int getTotalPrice() {      //스트림 로직으로 바꾼 것
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
//    }


}
