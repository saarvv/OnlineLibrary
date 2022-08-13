package com.example.onlinelibrary.dto;


import com.example.onlinelibrary.constraint.FieldMatch;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@FieldMatch.List({
        @FieldMatch(first = "username", second = "confirmUsername", message = "The username fields must match"),
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
public class UserDto {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    private Integer nationalCode;
}
