package com.crocobet.usermanagement.dto;

import com.crocobet.usermanagement.exception.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDTO {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;
    @Size(min = 8, max = 64)
    @StrongPassword
    private String password;

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password= **** " +
                '}';
    }
}
