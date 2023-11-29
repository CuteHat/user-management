package com.crocobet.usermanagement.dto;


import com.crocobet.usermanagement.exception.validation.StrongPassword;
import com.crocobet.usermanagement.persistence.model.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserUpdateDTO {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;
    @Size(min = 8, max = 64)
    @StrongPassword
    private String password;
    private RoleName role;

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", password= **** " +
                '}';
    }
}
