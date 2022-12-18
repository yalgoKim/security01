package com.security.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpRequestDto {

    private String email;
    private String password;
    private String name;

}
