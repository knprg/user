package com.auth1.auth.learning.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    private String name;
    private String email;
    private String password;
}
