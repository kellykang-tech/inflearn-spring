package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)    //거울
    private Order order;

    private Address address;

    @Enumerated(EnumType.STRING)    //ORDINAL 절대 쓰면 안된다. db 장애난다.
    private DeliveryStatus status;  //READY, COMP

}

