package ru.mirea.cnoss.model.authorization.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
}
