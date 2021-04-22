package jpabook.jpashop.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    /*
     * Embedded나 Embeddable 둘 중에 하나만 적어줘도 됨
     */
    @Embedded
    private Address address;

    /*
     *나는 매핑 된 거울일 뿐이야. ==읽기전용. 여기에 값을 넣는 다고 해서 order 의 foreign key 값이 변동 되지 않는다.
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
