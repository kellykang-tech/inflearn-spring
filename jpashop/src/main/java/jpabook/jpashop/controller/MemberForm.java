package jpabook.jpashop.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")   //붙이기만 하면 되는 게 아니라 @Valid 해줘야 함
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
